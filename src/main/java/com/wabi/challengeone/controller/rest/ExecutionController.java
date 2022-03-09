package com.wabi.challengeone.controller.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wabi.challengeone.annotation.LogExecutionTime;
import com.wabi.challengeone.component.client.ExternalServiceClient;
import com.wabi.challengeone.component.io.IOComponent;
import com.wabi.challengeone.component.sqlite.SQLiteComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
class ExecutionController {

    private final static Logger logger = LoggerFactory.getLogger(ExecutionController.class);

    @Autowired
    ExternalServiceClient externalServiceClient;

    @Autowired
    SQLiteComponent sqLiteComponent;

    @Autowired
    IOComponent ioComponent;

    @GetMapping("/execution0")
    @LogExecutionTime
    public Mono<String> execution0() {
        return WebClient
                .create()
                .get()
                .uri("https://mock.codes/503")
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(ExecutionController::execution1);
    }

    private static Mono<? extends String> execution1(Throwable throwable) {
        return WebClient
                .create()
                .get()
                .uri("http://www.google.com")
                .retrieve()
                .bodyToMono(String.class);
    }


    @GetMapping("/execution1")
    @LogExecutionTime
    public Mono<String> execution1() {
        return externalServiceClient.requestExternalWebflux();
    }

    @GetMapping("/execution2")
    @LogExecutionTime
    public String execution2() {
        return externalServiceClient.requestExternalResttemplate();
    }

    @GetMapping("/execution3")
    @LogExecutionTime
    public ResponseEntity<Object> execution3() {
        sqLiteComponent.runSqliteQuery();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/execution4")
    @LogExecutionTime
    public JsonNode execution4() throws IOException {
        return new ObjectMapper().readTree(ioComponent.createJsonString());
    }

    @GetMapping("/execution5")
    @LogExecutionTime
    public ResponseEntity<JsonNode> execution5() {
        try {
            return ResponseEntity.ok(new ObjectMapper().readTree(ioComponent.createJsonString()));
        } catch (IOException ioException) {
            JsonNode exceptionObject = new ObjectMapper().convertValue(ioException, JsonNode.class);
            return ResponseEntity.internalServerError().body(exceptionObject);
        }
    }


}
