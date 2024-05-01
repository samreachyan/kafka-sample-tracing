package com.sakcode.consumer;

import com.sakcode.common.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BkgListenerService {

    @KafkaListener(id = "info", topics = "${app.in.topic}")
    public void onMessage(@Payload Info info,
                          @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) Long key,
                          @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received(key={}, partition={}): {}", key, partition, info);

        // TODO:
        log.info("Completed here!!!");
    }

}
