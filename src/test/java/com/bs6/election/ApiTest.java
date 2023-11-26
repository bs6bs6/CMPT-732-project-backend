package com.bs6.election;


import com.bs6.election.common.Constants;
import com.bs6.election.model.ChatCompletionRequest;
import com.bs6.election.model.ChatCompletionResponse;
import com.bs6.election.model.Message;
import com.bs6.election.util.openAiApi.IOpenAiSession;
import com.bs6.election.util.openAiApi.OpenAiSessionFactory;
import com.bs6.election.util.openAiApi.OpenApiConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j

public class ApiTest {

    IOpenAiSession openAiSession;
    @Before
    public void init(){
        OpenApiConfiguration configuration = new OpenApiConfiguration();
        configuration.setApiHost("https://api.openai.com/");
        configuration.setApiKey("");

        OpenAiSessionFactory factory = new OpenAiSessionFactory(configuration);
        this.openAiSession = factory.getOpenAiSession();
    }


    @Test
    public void test_chat_completions() {
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

        chatCompletionResponse.getChoices().forEach(e -> {
            log.info("测试结果：{}", e.getMessage());
        });
    }
}
