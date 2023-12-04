package com.bs6.election.controller;


import com.bs6.election.common.Constants;
import com.bs6.election.model.ChatCompletionRequest;
import com.bs6.election.model.ChatCompletionResponse;
import com.bs6.election.model.Message;
import com.bs6.election.util.openAiApi.IOpenAiSession;
import com.bs6.election.util.openAiApi.OpenAiSessionFactory;
import com.bs6.election.util.openAiApi.OpenApiConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
public class OpenAiController {

    private Logger logger = LoggerFactory.getLogger(OpenAiController.class);
    IOpenAiSession openAiSession;

    @CrossOrigin
    @RequestMapping("/completion")
    public ArrayList<String> completion(){
        OpenApiConfiguration configuration = new OpenApiConfiguration();
        configuration.setApiHost("https://api.openai.com/");
        configuration.setApiKey("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");

        OpenAiSessionFactory factory = new OpenAiSessionFactory(configuration);
        this.openAiSession = factory.getOpenAiSession();
        String question = "Here are the top 5 words in a state in US appearing in the tweets" +
                "about 2020 US election final round during that specific period. Can u help me analyse" +
                " the attitude of this state towards the candidates Trump and Biden? Your answer should be a simple short sentence with a clear attitude. ";
        List<String> words = new ArrayList<>();
        words.add("COVID19");
        words.add("realDonaldTrump");
        words.add("MAGA");
        words.add("TrumpIsALaughingStock");
        words.add("nypost");
        question+=words.toString();

        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content(question).build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();

        ChatCompletionResponse chatCompletionResponse = openAiSession.completions(chatCompletion);
        ArrayList<String> res = new ArrayList<>();
        chatCompletionResponse.getChoices().forEach(e -> {
            logger.info("测试结果：{}", e.getMessage());
            res.add(e.getMessage().toString());
        });

        return res;
    }

}
