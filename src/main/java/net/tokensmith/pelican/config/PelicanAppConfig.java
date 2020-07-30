package net.tokensmith.pelican.config;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.tokensmith.pelican.Publish;
import net.tokensmith.pelican.Subscribe;
import net.tokensmith.pelican.kafka.KafkaProps;
import net.tokensmith.pelican.kafka.KafkaPublish;
import net.tokensmith.pelican.kafka.KafkaSubscribe;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Collection;
import java.util.Properties;

public class PelicanAppConfig {
    private static String MQ_ENV_VAR = "MESSAGE_QUEUE_HOST";
    private static String EMPTY = "";
    private static String MQ_ENV_VAR_INVALID = "Environment Variable, MESSAGE_QUEUE_HOST is invalid";
    private static String host;

    /**
     * Set the host to connect for publishing and subscribing.
     * @param host such as, localhost:9092
     */
    public void setMessageQueueHost(String host) {
        this.host = host;
    }

    public String messageQueueHost() {
        if (this.host == null || this.host.isEmpty() ) {
            this.host = System.getenv(MQ_ENV_VAR);
            if (host == null || host.equals(EMPTY)) {
                throw new RuntimeException(MQ_ENV_VAR_INVALID);
            }
        }
        return this.host;
    }

    public ObjectMapper objectMapper() {
        ObjectMapper om =  new ObjectMapper()
                .setPropertyNamingStrategy(
                        PropertyNamingStrategy.SNAKE_CASE
                )
                .configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        return om;
    }

    public Properties propertiesForPublish(String clientId) {
        Properties props = new Properties();
        props.put(KafkaProps.SERVER.getValue(), messageQueueHost());
        props.put(KafkaProps.CLIENT_ID.getValue(), clientId);
        props.put(KafkaProps.ACK.getValue(), KafkaProps.ALL.getValue());
        props.put(KafkaProps.RETRIES.getValue(), 0);
        props.put(KafkaProps.BATCH_SIZE.getValue(), 16384);
        props.put(KafkaProps.LINGER.getValue(), 1);
        props.put(KafkaProps.BUFFER_SIZE.getValue(), 33554432);
        props.put(KafkaProps.KEY_SERIALIZER.getValue(), "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put(KafkaProps.VALUE_SERIALIZER.getValue(), "org.apache.kafka.common.serialization.ByteArraySerializer");

        return props;
    }

    public Properties propertiesForSubscribe(String clientId, String consumerGroup) {
        Properties props = new Properties();

        props.put(KafkaProps.SERVER.getValue(), messageQueueHost());
        props.put(KafkaProps.CLIENT_ID.getValue(), clientId);
        props.put(KafkaProps.GROUP_ID.getValue(), consumerGroup);
        props.put(KafkaProps.ENABLE_AUTO_COMMIT.getValue(), "true");
        props.put(KafkaProps.AUTO_COMMIT_INTERVAL.getValue(), "1000");
        props.put(KafkaProps.AUTO_OFFSET_RESET.getValue(), "earliest");
        props.put(KafkaProps.MAX_POLL_RECORDS.getValue(), "1");
        props.put(KafkaProps.KEY_DESERIALIZER.getValue(), "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(KafkaProps.VALUE_DESERIALIZER.getValue(), "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }
    public Publish publish(String clientId) {
        Properties props = propertiesForPublish(clientId);
        return new KafkaPublish(
            objectMapper(),
            new KafkaProducer<>(props)
        );
    }

    public KafkaConsumer<String, String> consumer(Collection<String> topics, String clientId, String consumerGroup){
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(propertiesForSubscribe(clientId, consumerGroup));
        consumer.subscribe(topics);
        return consumer;
    }

    public Subscribe subscribe(Collection<String> topics, String clientId, String consumerGroup) {
        return new KafkaSubscribe(consumer(topics, clientId, consumerGroup), objectMapper());
    }
}
