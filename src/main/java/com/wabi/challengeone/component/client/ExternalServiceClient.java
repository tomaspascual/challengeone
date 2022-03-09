package com.wabi.challengeone.component.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ExternalServiceClient {

    private final static Logger logger = LoggerFactory.getLogger(ExternalServiceClient.class);

    public Mono<String> requestExternalWebflux() {
        WebClient client = WebClient.builder().build();
        return client.get()
                .uri(URI.create("https://blockchain.info/tobtc?currency=USD&value=500"))
                .retrieve()
                .bodyToMono(String.class)
                .map(data -> {
                    logger.info(data);
                    return data;
                });
    }

    public String requestExternalResttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String returnable = restTemplate
                .exchange("https://blockchain.info/tobtc?currency=USD&value=500", HttpMethod.GET, RequestEntity.EMPTY, String.class)
                .getBody();
        logger.info(returnable);
        return returnable;
    }




}
