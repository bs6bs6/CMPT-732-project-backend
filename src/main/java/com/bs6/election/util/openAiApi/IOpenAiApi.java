package com.bs6.election.util.openAiApi;

import com.bs6.election.model.ChatCompletionRequest;
import com.bs6.election.model.ChatCompletionResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IOpenAiApi {

    String v1_chat_completions = "v1/chat/completions";
    @POST(v1_chat_completions)
    Single<ChatCompletionResponse> completions(@Body ChatCompletionRequest chatCompletionRequest);
}
