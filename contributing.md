# Contributing

## Docker
```bash
docker build docker/pelican-build -t pelican-build
docker run -i -t pelican-build
```

# Run Kafka in pelican-build.
```bash
$ chmod +x run_kafka.sh
$ ./run_kafka.sh
```

# Execute tests in pelican-build
```bash
gradle assemble -x signArchives
```

