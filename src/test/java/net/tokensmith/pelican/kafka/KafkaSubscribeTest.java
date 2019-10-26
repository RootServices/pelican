package net.tokensmith.pelican.kafka;

import net.tokensmith.pelican.Publish;
import net.tokensmith.pelican.config.PelicanAppConfig;
import org.junit.Before;
import org.junit.Test;
import net.tokensmith.pelican.Subscribe;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class KafkaSubscribeTest {
    private PelicanAppConfig appConfig;

    @Before
    public void setUp() {
        appConfig = new PelicanAppConfig();
        appConfig.setMessageQueueHost("localhost:9092");
    }


    @Test(timeout=20000)
    public void pollShouldGetMessage() {
        Map<String, String> message = new HashMap<>();
        message.put("test_key", "test_value");

        String subscribeClientId = "pelican-subscribe-integration-test-1";
        String consumerGroup = "pelican-consumer-group-integration-test-1";
        Subscribe subject = appConfig.subscribe(Arrays.asList("test"), subscribeClientId, consumerGroup);

        String publishClientId = "pelican-publisher-integration-test-1";
        Publish publish = appConfig.publish(publishClientId);
        publish.send("test", message);

        Duration timeout = Duration.ofSeconds(100);
        List<Map<String, String>> actual = subject.poll(timeout);

        subject.processed();

        assertThat("subscriber did not get a message", actual, is(notNullValue()));
        assertThat("message the subscriber got was empty", actual.size(), is(1));
        assertThat("message has a unexpected value for key, test_key", actual.get(0).get("test_key"), is("test_value"));
    }
}