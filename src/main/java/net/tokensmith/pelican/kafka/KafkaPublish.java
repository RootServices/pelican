package net.tokensmith.pelican.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.tokensmith.pelican.Publish;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;
import java.util.Properties;

public class KafkaPublish implements Publish {
    protected static Logger LOGGER = LoggerFactory.getLogger(KafkaPublish.class);

    private Producer<String, byte[]> producer;
    private ObjectMapper objectMapper;

    public KafkaPublish(ObjectMapper objectMapper, Producer<String, byte[]> producer) {
        this.objectMapper = objectMapper;
        this.producer = producer;
    }

    @Override
    public void send(String topic, Map<String, String> msg) {
        byte[] payload = new byte[0];

        try {
            payload = objectMapper.writeValueAsBytes(msg);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        producer.send(new ProducerRecord<>(topic, payload));
        LOGGER.debug("sent message");
    }

    @Override
    public void close() {
        producer.close();
    }
}
