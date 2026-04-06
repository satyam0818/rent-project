package com.rent.strategy;

public class UpiPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing UPI payment of $" + amount);
        return true;
    }
}
