package com.sakcode.consumer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;

@Configurable
public class KafkaConfiguration {

    @Value("${app.in.topic}")
    private String topic;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory(ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setObservationEnabled(true);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public NewTopic infoTopic() {
        return TopicBuilder.name(topic)
                .partitions(10)
                .replicas(3)
                .build();
    }

}
