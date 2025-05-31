package com.coffecode.paymentservice.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentStrategyFactory {

    private final Map<String, PaymentStrategy> strategies;

    @Autowired
    public PaymentStrategyFactory(List<PaymentStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(PaymentStrategy::getStrategyName, Function.identity()));
    }

    public PaymentStrategy getStrategy(String strategyName) {
        if (strategyName == null) {
            throw new IllegalArgumentException("El método de pago no puede ser nulo.");
        }
        PaymentStrategy strategy = strategies.get(strategyName.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Método de pago no soportado: " + strategyName);
        }
        return strategy;
    }
}
