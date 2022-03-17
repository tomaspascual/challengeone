package com.wabi.challengeone.component.strategies;

import com.wabi.challengeone.strategy.OperationA;
import com.wabi.challengeone.strategy.OperationB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StrategyComponent {

    private final static Logger logger = LoggerFactory.getLogger(StrategyComponent.class);


    @Autowired
    OperationA operationA;

    @Autowired
    OperationB operationB;

    public void strategyExample(Optional<String> fiat, int amount) {

         fiat.ifPresentOrElse(
                (object) -> {
                    if (object.equals("EUR")) {
                        operationB.applyStrategy(amount).doOnNext(logger::info).subscribe(); //lets subscribe just for testing purposes
                    } else {
                        operationA.applyStrategy(amount).doOnNext(logger::info).subscribe(); //lets subscribe just for testing purposes
                    }
                },
                () -> {
                    logger.error("No fiat present");
                }
        );

    }

}
