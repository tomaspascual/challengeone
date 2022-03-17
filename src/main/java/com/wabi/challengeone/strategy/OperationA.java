package com.wabi.challengeone.strategy;

import com.wabi.challengeone.strategy.interfaces.StrategyOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class OperationA implements StrategyOperation {

    private final static Logger logger = LoggerFactory.getLogger(OperationA.class);

    @Override
    public Mono<String> applyStrategy(int howMany) {
        WebClient client = WebClient.builder().build();
        return client.get()
                .uri(URI.create("https://blockchain.info/tobtc?currency=USD&value="+howMany))
                .retrieve()
                .bodyToMono(String.class);
    }
}
