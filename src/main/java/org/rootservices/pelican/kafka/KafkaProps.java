package org.rootservices.pelican.kafka;

public enum KafkaProps {
    SERVER ("bootstrap.servers"),
    CLIENT_ID ("client.id"),
    ACK ("acks"),
    ALL ("all"),
    RETRIES("retries"),
    BATCH_SIZE("batch.size"),
    LINGER ("linger.ms"),
    BUFFER_SIZE ("buffer.memory"),
    KEY_SERIALIZER ("key.serializer"),
    VALUE_SERIALIZER ("value.serializer"),
    KEY_DESERIALIZER ("key.deserializer"),
    VALUE_DESERIALIZER ("value.deserializer"),
    GROUP_ID ("group.id"),
    ENABLE_AUTO_COMMIT ("enable.auto.commit"),
    AUOT_COMMIT_INTERVAL ("auto.commit.interval.ms");

    private String value;

    KafkaProps(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
