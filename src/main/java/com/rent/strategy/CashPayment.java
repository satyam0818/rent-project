package com.rent.strategy;

public class CashPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing cash payment of $" + amount);
        return true;
    }
}
