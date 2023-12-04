package com.bs6.election.kafka;

import com.alibaba.fastjson.JSON;
import com.bs6.election.model.PredictDTO;
import com.bs6.election.model.PredictVO;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducer {
    private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public static final String TOPIC_PREDICT_REQUEST = "election-predict-request";

    public static final String TOPIC_PREDICT_RESPONSE = "election-predict-response";

    public CompletableFuture<SendResult<String, Object>> sendPredictRequest(PredictDTO predict) {
        String objJson = JSON.toJSONString(predict);
        logger.info("Sending MQ message... topic：{},message：{}", TOPIC_PREDICT_REQUEST, objJson);
        return kafkaTemplate.send(TOPIC_PREDICT_REQUEST, objJson);
    }



}
