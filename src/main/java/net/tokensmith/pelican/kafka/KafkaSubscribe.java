package net.tokensmith.pelican.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import net.tokensmith.pelican.Subscribe;

import java.io.IOException;
import java.time.Duration;
import java.util.*;


public class KafkaSubscribe implements Subscribe {
    protected static Logger logger = LogManager.getLogger(KafkaSubscribe.class);

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
            logger.debug("partitions: " + partitions);

            logger.debug("polling for message");
            ConsumerRecords<String, String> records = consumer.poll(timeout);
            logger.debug("records: " + records.count());

            for (ConsumerRecord<String, String> record : records) {
                try {
                    logger.debug("msg offset: " + record.offset());
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

    @Override
    public void processed() {
        consumer.commitSync();
    }
}
