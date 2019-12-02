package com.amoser.demometrics.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqsConfig {

    @Value("${cloud.aws.region.static}")
    private String awsRegion;

    @Bean
    public AmazonSQSAsync amazonSQS(LocalStackProperties properties) {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(new AmazonSQSAsyncClientBuilder.EndpointConfiguration(properties.getUrl(), awsRegion))
                .build();
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSQSClient, LocalStackProperties properties) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQSClient);
        factory.setMaxNumberOfMessages(properties.getMaxMessage());
        return factory;
    }
}
