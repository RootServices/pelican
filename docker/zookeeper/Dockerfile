FROM rs/kafka

RUN \
    mkdir -p /data/scripts/

COPY run_zookeeper.sh /data/scripts/run_zookeeper.sh

RUN \
    chmod +x /data/scripts/run_zookeeper.sh

EXPOSE 2181

CMD ["sh"]
