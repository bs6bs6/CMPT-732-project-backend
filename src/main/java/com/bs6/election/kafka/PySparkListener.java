package com.bs6.election.kafka;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.bs6.election.model.PredictResult;
import com.bs6.election.service.EmitterService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;

@Component
public class PySparkListener {
    private final Logger logger = LoggerFactory.getLogger(PySparkListener.class);

    @Autowired
    EmitterService emitterService;
    @KafkaListener(topics = "election-predict-response", groupId = "CMPT_732")
    public void onMessage(ConsumerRecord<?,?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws InterruptedException {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isEmpty()) {
            return;
        }
        try{
            PredictResult result = JSON.parseObject((String) message.get(), PredictResult.class);
            SseEmitter emitter = emitterService.getEmitter(result.getUuid());
            if (emitter != null) {
                try {
                    emitter.send(result);
                    logger.info("emitter deleted successfully");

                } catch (IOException e) {
                    logger.info("completeWithError");

                    emitter.completeWithError(e);
                }
            }
            logger.info("consumption complete topic：{} result：{}", topic, result);

            ack.acknowledge();
        }catch(Exception e){
            logger.error("consumption failed topic：{} message：{}", topic, message.get());
            throw e;
        }
    }




}
