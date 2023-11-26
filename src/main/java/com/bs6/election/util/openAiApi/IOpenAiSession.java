package com.bs6.election.util.openAiApi;

import com.bs6.election.model.ChatCompletionRequest;
import com.bs6.election.model.ChatCompletionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

public interface IOpenAiSession {


    ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest);
    EventSource chatCompletions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;




}
