FROM rs/kafka


RUN \
    mkdir /data/config && \
    mkdir /data/scripts

COPY run_kafka.sh /data/scripts/run_kafka.sh
COPY server.properties /data/config/server.properties

RUN \
    chmod +x /data/scripts/run_kafka.sh

EXPOSE 9092

CMD ["sh"]
