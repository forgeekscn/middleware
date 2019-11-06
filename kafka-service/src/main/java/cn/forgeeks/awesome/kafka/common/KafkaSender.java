package cn.forgeeks.awesome.kafka.common;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class KafkaSender {

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    private Gson gson = new GsonBuilder().create();
//
//    //发送消息方法
//    public void send() {
//        Message message = new Message();
//        message.setId(System.currentTimeMillis());
//        message.setMsg(UUID.randomUUID().toString());
//        message.setSendTime(new Date());
//        log.info("+++++++++++++++++++++  message = {}", gson.toJson(message));
//        kafkaTemplate.send("TOPIC_KAFKA_TEST", gson.toJson(message));
//    }
}