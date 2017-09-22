package org.rootservices.pelican.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.rootservices.pelican.Subscribe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class KafkaSubscribe implements Subscribe {
    private KafkaConsumer<String, String> consumer;
    private ObjectMapper objectMapper;

    public KafkaSubscribe(KafkaConsumer<String, String> consumer, ObjectMapper objectMapper) {
        this.consumer = consumer;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, String> poll(long timeout) {
        Map<String, String> msg = new HashMap<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(timeout);
            for (ConsumerRecord<String, String> record : records)
                try {
                    msg = objectMapper.readValue(record.value(), new TypeReference<Map<String, String>>(){});
                    return msg;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }
}
