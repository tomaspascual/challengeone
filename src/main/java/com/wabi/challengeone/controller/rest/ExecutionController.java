package com.wabi.challengeone.controller.rest;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wabi.challengeone.annotation.LogExecutionTime;
import com.wabi.challengeone.component.client.ExternalServiceClient;
import com.wabi.challengeone.component.io.IOComponent;
import com.wabi.challengeone.component.sqlite.SQLiteComponent;
import com.wabi.challengeone.component.strategies.StrategyComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Optional;


@RestController
class ExecutionController {

    private final static Logger logger = LoggerFactory.getLogger(ExecutionController.class);

    @Autowired
    ExternalServiceClient externalServiceClient;

    @Autowired
    SQLiteComponent sqLiteComponent;

    @Autowired
    IOComponent ioComponent;

    @Autowired
    StrategyComponent strategyComponent;

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

    @RequestMapping(value={"/execution6/{amount}","/execution6/{amount}/{fiat}"}, method = RequestMethod.GET)
    @LogExecutionTime
    public ResponseEntity execution6(@PathVariable(required = true) Integer amount, @PathVariable(required = false) String fiat) {
        strategyComponent.strategyExample(Optional.ofNullable(fiat), amount);
        return ResponseEntity.ok().build();
    }


}
