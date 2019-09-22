
build-docker:
	docker-compose up --no-start

start:
	docker-compose up -d

stop:
	docker-compose stop