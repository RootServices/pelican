
build-docker:
	docker-compose up --no-start

start:
	docker-compose start zookeeper kafka-broker

stop:
	docker-compose stop