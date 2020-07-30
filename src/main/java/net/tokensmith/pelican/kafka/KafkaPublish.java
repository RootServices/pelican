package net.tokensmith.pelican.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.tokensmith.pelican.Publish;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;
import java.util.Properties;

public class KafkaPublish implements Publish {
    protected static Logger logger = LoggerFactory.getLogger(KafkaPublish.class);

    private Properties properties;
    private Producer<String, byte[]> producer;
    private ObjectMapper objectMapper;

    public KafkaPublish(Properties properties, ObjectMapper objectMapper, Producer<String, byte[]> producer) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.producer = producer;
    }

    @Override
    public void send(String topic, Map<String, String> msg) {
        byte[] payload = new byte[0];

        try {
            payload = objectMapper.writeValueAsBytes(msg);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        producer.send(new ProducerRecord<>(topic, payload));
        logger.debug("sent message");

        logger.debug("closed connection");
    }

    @Override
    public void close() {
        producer.close();
    }
}
