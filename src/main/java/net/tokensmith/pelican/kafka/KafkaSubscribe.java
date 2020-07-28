package net.tokensmith.pelican.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.tokensmith.pelican.Subscribe;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class KafkaSubscribe implements Subscribe {
    protected static Logger LOGGER = LoggerFactory.getLogger(KafkaSubscribe.class);

    private KafkaConsumer<String, String> consumer;
    private ObjectMapper objectMapper;

    public KafkaSubscribe(KafkaConsumer<String, String> consumer, ObjectMapper objectMapper) {
        this.consumer = consumer;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Map<String, String>> poll(Duration timeout) {
        List<Map<String, String>> msgs = new ArrayList<>();
        while (true) {
            msgs.clear();

            Set<TopicPartition> partitions = consumer.assignment();
            LOGGER.trace("partitions: " + partitions);

            LOGGER.trace("polling for message");
            ConsumerRecords<String, String> records = consumer.poll(timeout);
            LOGGER.trace("records: " + records.count());

            for (ConsumerRecord<String, String> record : records) {
                try {
                    LOGGER.trace("msg offset: " + record.offset());
                    Map<String, String> msg = objectMapper.readValue(record.value(), new TypeReference<Map<String, String>>(){});
                    msgs.add(msg);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (msgs.size() > 0) {
                LOGGER.trace("returning messages");
                return msgs;
            }
        }
    }

    @Override
    public void processed() {
        consumer.commitSync();
    }
}
