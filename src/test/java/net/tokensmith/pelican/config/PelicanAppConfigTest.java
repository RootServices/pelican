package net.tokensmith.pelican.config;


import helper.PubSubConfig;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PelicanAppConfigTest {

    public PelicanAppConfig subject() {
        return new PelicanAppConfig();
    }

    @Test
    public void setMessageQueueHostShouldAssign() {
        PelicanAppConfig subject = subject();
        subject.setMessageQueueHost("localhost:1234");
        assertThat(subject.messageQueueHost(), is("localhost:1234"));
    }

    @Test
    public void canOverridePropertiesForPublish() {
        PubSubConfig subject = new PubSubConfig();
        Properties actual = subject.propertiesForPublish("clientId");
        assertThat(actual.get("my.new.prop.key"), is("my.new.prop.value"));
    }

    @Test
    public void canOverridePropertiesForSubscribe() {
        PubSubConfig subject = new PubSubConfig();
        Properties actual = subject.propertiesForSubscribe("clientId", "consumerGroup");
        assertThat(actual.get("my.new.prop.key"), is("my.new.prop.value"));
    }

}