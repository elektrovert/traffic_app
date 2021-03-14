package com.eagerforlife.traffic.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class TrafficMessageClient {

    private final WebClient client;

    public TrafficMessageClient() {
        // Create the web client, there are many more options we could modify here
        // Read/Write timeout handlers for example
        client = WebClient.create("http://api.sr.se");
    }

    public String getNotifications() {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/api/v2/traffic/messages");
        WebClient.ResponseSpec responseSpec = bodySpec.accept(MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve();
        Mono<String> response = responseSpec.bodyToMono(String.class);

        return response.block();
    }

}
