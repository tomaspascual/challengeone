package com.wabi.challengeone.strategy.interfaces;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface StrategyOperation {

    Mono<String> applyStrategy(int howMany);

}
