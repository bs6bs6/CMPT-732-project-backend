package com.bs6.election.controller;

import com.bs6.election.kafka.KafkaProducer;
import com.bs6.election.model.PredictDTO;
import com.bs6.election.model.PredictVO;
import com.bs6.election.service.EmitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Controller
@CrossOrigin
public class EmitterController {
    Logger logger = LoggerFactory.getLogger(EmitterController.class);

    @Autowired
    KafkaProducer kafkaProducer;
    @Autowired
    private EmitterService emitterService;

    @CrossOrigin
    @PostMapping("/getPrediction")
    @ResponseBody
    public Map<String,String> getPrediction(@RequestBody PredictVO predict){
        PredictDTO message = new PredictDTO();
        String requestId = UUID.randomUUID().toString();

        message.setCandidate(predict.getCandidate());
        message.setHour(predict.getHour());
        message.setDayofweek(predict.getDayofweek());
        message.setSentiment(predict.getSentiment());
        message.setLanguage(predict.getLanguage());
        message.setOnline_age(predict.getOnline_age());
        message.setUuid(requestId);

        CompletableFuture<SendResult<String,Object>> future = kafkaProducer.sendPredictRequest(message);
        future.thenAccept(sendResult -> {
            logger.info("kafka sending success");
        }).exceptionally(throwable -> {
            logger.info("kafka sending fail");
            return null;
        });

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitter.onCompletion(() -> emitterService.removeEmitter(requestId));
        emitter.onTimeout(() -> emitterService.removeEmitter(requestId));
        emitter.onError((e) -> emitterService.removeEmitter(requestId));
        emitterService.addEmitter(requestId, emitter);

        Map<String,String> res = new HashMap<>();
        res.put("requestId",requestId);
        return res;
    }

    @CrossOrigin
    @GetMapping("/getSSE")
    public SseEmitter getSSE(@RequestParam String requestId){
        logger.info(requestId);
        SseEmitter sseEmitter = emitterService.getEmitter(requestId);
        logger.info(sseEmitter.toString());
        return sseEmitter;
    }

    @CrossOrigin
    @GetMapping("/getTest")
    public SseEmitter getTest(){
        PredictVO predict = new PredictVO(8,13,13,"Trump","English","");
        PredictDTO message = new PredictDTO();
        String requestId = UUID.randomUUID().toString();

        message.setCandidate(predict.getCandidate());
        message.setHour(predict.getHour());
        message.setDayofweek(predict.getDayofweek());
        message.setSentiment(predict.getSentiment());
        message.setLanguage(predict.getLanguage());
        message.setOnline_age(predict.getOnline_age());
        message.setUuid(requestId);

        CompletableFuture<SendResult<String,Object>> future = kafkaProducer.sendPredictRequest(message);
        future.thenAccept(sendResult -> {
            logger.info("kafka sending success");
        }).exceptionally(throwable -> {
            logger.info("kafka sending fail");
            return null;
        });

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitter.onCompletion(() -> emitterService.removeEmitter(requestId));
        emitter.onTimeout(() -> emitterService.removeEmitter(requestId));
        emitter.onError((e) -> emitterService.removeEmitter(requestId));

        emitterService.addEmitter(requestId,emitter);
        logger.info("emitter send successfully");
        return emitter;
    }
}
