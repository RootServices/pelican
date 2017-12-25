FROM rs/kafka

RUN \
    mkdir -p /build/rootservices/scripts && \
    mkdir -p /build/rootservices/config

COPY scripts/run_zookeeper.sh /build/rootservices/scripts/run_zookeeper.sh
COPY scripts/run_kafka.sh /build/rootservices/scripts/run_kafka.sh
COPY config/server.properties /build/rootservices/config/server.properties

RUN \
    chmod +x /build/rootservices/scripts/*.sh

ARG GRADLE_VERSION=4.4
ARG GRADLE_HOME="/opt/gradle"
ARG USER_HOME_DIR="/root"

RUN \
  wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -O /tmp/gradle.zip && \
  unzip /tmp/gradle.zip && \
  rm /tmp/gradle.zip && \
  mkdir -p ${GRADLE_HOME} && \
  mv gradle-${GRADLE_VERSION} ${GRADLE_HOME} && \
  ln -s ${GRADLE_HOME}/gradle-${GRADLE_VERSION}/bin/gradle /usr/bin/gradle && \
  mkdir -p ${USER_HOME_DIR}/gradle/.gradle


ENV GRADLE_HOME ${GRADLE_HOME}
ENV MESSAGE_QUEUE_HOST='localhost:9092'

# Define working directory.
WORKDIR /build/rootservices

EXPOSE 2181 9092

# Define default command.
CMD ["bash"]
