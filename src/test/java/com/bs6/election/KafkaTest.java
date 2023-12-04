package com.bs6.election;


import com.bs6.election.kafka.KafkaProducer;
import com.bs6.election.model.PredictDTO;
import com.bs6.election.model.PredictVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTest {
    Logger logger = LoggerFactory.getLogger(KafkaTest.class);
    @Autowired
    KafkaProducer kafkaProducer;
    @Test
    public void kafkaTest(){
        PredictVO predict = new PredictVO(8, 3, 13, "Trump", "English", "");
        PredictDTO message = new PredictDTO();
        String requestId = UUID.randomUUID().toString();

        message.setCandidate(predict.getCandidate());
        message.setHour(predict.getHour());
        message.setDayofweek(predict.getDayofweek());
        message.setSentiment(predict.getSentiment());
        message.setLanguage(predict.getLanguage());
        message.setOnline_age(predict.getOnline_age());
        message.setUuid(requestId);

        logger.info(message.toString());
        kafkaProducer.sendPredictRequest(message);
    }
}
