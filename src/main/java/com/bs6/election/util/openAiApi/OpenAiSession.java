package com.bs6.election.util.openAiApi;

import com.bs6.election.model.ChatCompletionRequest;
import com.bs6.election.model.ChatCompletionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.reactivex.Single;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OpenAiSession implements IOpenAiSession {
    Logger logger = LoggerFactory.getLogger(OpenAiSession.class);
    private final OpenApiConfiguration openApiConfiguration;
    private final IOpenAiApi openAiApi;
    private final EventSource.Factory factory;

    public OpenAiSession (OpenApiConfiguration openApiConfiguration){
        this.openApiConfiguration = openApiConfiguration;
        this.openAiApi = openApiConfiguration.getOpenAiApi();
        this.factory = openApiConfiguration.createRequestFactory();
    }


    @Override
    public ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest) {
        return this.openAiApi.completions(chatCompletionRequest).blockingGet();
    }

    @Override
    public EventSource chatCompletions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
        return null;
    }
}
