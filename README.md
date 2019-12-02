# demo-spring-prometheus

## How to run this demo

In `docker/prometheus.yaml` edit `targets: ['172.17.0.1:8080']` with the IP
of your Spring Boot application.

```
docker-compose up
```

Run Spring Boot application `mvn spring-boot:run`

## Prometheus

Connect to http://localhost:9090

## AWS Sqs

Create queue :
```
aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name test_queue
```

List queues :
```
aws --endpoint-url=http://localhost:4576 sqs list-queues
```

Send Message in queue :
```
aws --endpoint-url=http://localhost:4576 sqs send-message --queue-url http://localhost:4576/queue/test_queue --message-body "My Message"
```