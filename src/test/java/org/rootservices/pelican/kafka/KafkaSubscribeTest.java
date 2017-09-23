package org.rootservices.pelican.kafka;

import org.junit.Before;
import org.junit.Test;
import org.rootservices.pelican.Publish;
import org.rootservices.pelican.Subscribe;
import org.rootservices.pelican.config.AppConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class KafkaSubscribeTest {
    private AppConfig appConfig;

    @Before
    public void setUp() {
        appConfig = new AppConfig();
    }

    @Test
    public void pollShouldGetMessage() {
        Map<String, String> message = new HashMap<>();
        message.put("test_key", "test_value");

        Subscribe subject = appConfig.subscribe(Arrays.asList("test"), "test");

        Publish publish = appConfig.publish();
        publish.send("test", message);

        Map<String, String> actual = subject.poll(100);

        assertThat("subscriber did not get a message", actual, is(notNullValue()));
        assertThat("message the subscriber got was empty", actual.size(), is(1));
        assertThat("message has a unexpected value for key, test_key", actual.get("test_key"), is("test_value"));
    }

}