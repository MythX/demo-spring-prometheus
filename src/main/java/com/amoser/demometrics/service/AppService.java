package com.amoser.demometrics.service;

import com.amoser.demometrics.config.LocalStackProperties;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppService.class);

    private final MeterRegistry registry;

    public AppService(MeterRegistry registry) {
        this.registry = registry;
    }

    public void helloSample() {
        Timer timer = Timer.builder("appservice").tag("method", "manual").register(registry);
        Timer.Sample sample = Timer.start(registry);
        doSomething();
        sample.stop(timer);
    }

    @Scheduled(fixedRate = 1000)
    public void testMethod() {
        helloSample();
    }

    @SqsListener("${sqs.queue}")
    public void queueListener(String test) {
        LOGGER.info("SQS Message " + test);

        Timer timer = Timer.builder("appservice")
                .tag("method", "queue")
                .publishPercentiles(0.5, 0.95)
                .register(registry);
        Timer.Sample sample = Timer.start(registry);

        doSomething(50, 5000);

        sample.stop(timer);
    }

    private void doSomething(int min, int max) {
        Random r = new Random();
        int result = r.nextInt((max - min) + 1) + min;
        try {
            Thread.sleep(result);
        } catch (InterruptedException e) {
            //
        }
    }

    private void doSomething() {
        doSomething(50, 50);
    }

}
