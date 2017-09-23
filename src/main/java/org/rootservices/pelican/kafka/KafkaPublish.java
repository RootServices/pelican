package org.rootservices.pelican.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.rootservices.pelican.Publish;

import java.util.Map;
import java.util.Properties;

public class KafkaPublish implements Publish {
    protected static Logger logger = LogManager.getLogger(KafkaPublish.class);

    Properties properties;
    ObjectMapper objectMapper;

    public KafkaPublish(Properties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @Override
    public void send(String topic, Map<String, String> msg) {
        byte[] payload = new byte[0];

        try {
            payload = objectMapper.writeValueAsBytes(msg);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }

        Producer<String, byte[]> producer = new KafkaProducer<>(properties);
        producer.send(new ProducerRecord<>(topic, payload));
        producer.close();
    }
}
