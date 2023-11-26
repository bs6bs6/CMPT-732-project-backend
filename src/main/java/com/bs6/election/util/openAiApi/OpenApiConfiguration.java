package com.bs6.election.util.openAiApi;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;


@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiConfiguration {

    @Getter
    @Setter
    private IOpenAiApi openAiApi;

    @Getter
    @Setter
    private OkHttpClient okHttpClient;

    @Getter
    private String apiKey;

    @Getter
    private String apiHost;

    @Getter
    private String authToken;

    public EventSource.Factory createRequestFactory() {
        return EventSources.createFactory(okHttpClient);
    }

}
