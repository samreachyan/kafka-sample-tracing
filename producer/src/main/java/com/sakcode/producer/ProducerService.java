package com.sakcode.producer;

import com.sakcode.common.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class ProducerService {

    AtomicLong id = new AtomicLong();
    @Autowired
    KafkaTemplate<Long, Info> template;

    @Value("${kafka-producer}")
    private String pod;
    @Value("${NAMESPACE:empty}")
    private String namespace;
    @Value("${CLUSTER:localhost}")
    private String cluster;
    @Value("${TOPIC:info}")
    private String topic;

    @Scheduled(fixedRate = 1000)
    public void send() {
        Info info = new Info(id.incrementAndGet(), pod, namespace, cluster, "HELLO");
//        log.info("Information instant: {}", info);

        CompletableFuture<SendResult<Long, Info>> result = template.send(topic, info.getId(), info);
        result.whenComplete((sr, ex) -> log.info("Sent({}): {}", sr.getProducerRecord().key(), sr.getProducerRecord().value()));
    }


}
