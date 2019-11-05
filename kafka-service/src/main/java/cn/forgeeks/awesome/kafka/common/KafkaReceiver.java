package cn.forgeeks.awesome.kafka.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
@Slf4j
public class KafkaReceiver {

//    @KafkaListener(topics = {"TOPIC_KAFKA_TEST"})
//    public void listen(ConsumerRecord<?, ?> record) {
//
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//
//        if (kafkaMessage.isPresent()) {
//
//            Object message = kafkaMessage.get();
//
//            log.info("----------------- record =" + record);
//            log.info("------------------ message =" + message);
//        }
//
//    }
}