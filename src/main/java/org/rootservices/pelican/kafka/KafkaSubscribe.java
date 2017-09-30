package org.rootservices.pelican.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.rootservices.pelican.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KafkaSubscribe implements Subscribe {
    protected static Logger logger = LogManager.getLogger(KafkaSubscribe.class);

    private KafkaConsumer<String, String> consumer;
    private ObjectMapper objectMapper;

    public KafkaSubscribe(KafkaConsumer<String, String> consumer, ObjectMapper objectMapper) {
        this.consumer = consumer;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Map<String, String>> poll(long timeout) {
        List<Map<String, String>> msgs = new ArrayList<>();
        while (true) {
            msgs.clear();

            logger.debug("polling for message");
            ConsumerRecords<String, String> records = consumer.poll(timeout);
            logger.debug("records: " + records.count());

            for (ConsumerRecord<String, String> record : records) {
                try {
                    Map<String, String> msg = objectMapper.readValue(record.value(), new TypeReference<Map<String, String>>(){});
                    msgs.add(msg);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (msgs.size() > 0) {
                logger.debug("returning messages");
                return msgs;
            }
        }
    }
}
