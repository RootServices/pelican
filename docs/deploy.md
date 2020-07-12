How to Deploy
--------------------

### Deploy the application

```bash
./gradlew clean publish
```

### Deploy the docker images

```bash
make build
docker tag <image-id> tokensmith/kafka:v<X.X>
docker tag <image-id> tokensmith/zookeeper:v<X.X>
docker tag <image-id> tokensmith/kafka-broker:v<X.X>

docker push tokensmith/kafka:v<X.X>
docker push tokensmith/zookeeper:v<X.X>
docker push tokensmith/kafka-broker:v<X.X>
```


