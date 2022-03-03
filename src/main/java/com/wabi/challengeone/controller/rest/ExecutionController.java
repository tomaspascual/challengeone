package com.wabi.challengeone.controller.rest;


import com.wabi.challengeone.component.client.ExternalServiceClient;
import com.wabi.challengeone.component.sqlite.SQLiteComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
class ExecutionController {

    @Autowired
    ExternalServiceClient externalServiceClient;

    @Autowired
    SQLiteComponent sqLiteComponent;

    @GetMapping("/execution1")
    public Mono<String> execution1() {
        return externalServiceClient.requestExternalWebflux();
    }

    @GetMapping("/execution2")
    public String execution2() {
        return externalServiceClient.requestExternalResttemplate();
    }

    @GetMapping("/execution3")
    public void execution3() throws Exception {
        sqLiteComponent.runSqliteQuery();
    }

}
