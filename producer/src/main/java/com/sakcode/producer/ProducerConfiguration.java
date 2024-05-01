package com.sakcode.producer;

import com.sakcode.common.Info;
import io.micrometer.common.KeyValues;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.micrometer.KafkaRecordSenderContext;
import org.springframework.kafka.support.micrometer.KafkaTemplateObservationConvention;

@Configurable
@Slf4j
public class ProducerConfiguration {


    @Bean
    public NewTopic infoTopic() {
        return TopicBuilder.name("info").partitions(1).replicas(1).build();
    }

    @Bean
    public KafkaTemplate<Long, Info> kafkaTemplate(ProducerFactory<Long, Info> producerFactory) {
        KafkaTemplate<Long, Info> t = new KafkaTemplate<>(producerFactory);
        t.setObservationEnabled(true);
        t.setObservationConvention(new KafkaTemplateObservationConvention() {
            @Override
            public KeyValues getLowCardinalityKeyValues(KafkaRecordSenderContext context) {
                return KeyValues.of("topic", context.getDestination(), "id", String.valueOf(context.getRecord().key()));
            }
        });
        return t;
    }

}
