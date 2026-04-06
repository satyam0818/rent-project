package com.rent.strategy;

public class CardPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing Card payment of $" + amount);
        return true;
    }
}
