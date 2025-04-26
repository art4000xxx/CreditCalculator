package org.example.creditcalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class CreditCalculator {
    // Рассчитывает месячный платеж по формуле аннуитета
    public double calculateMonthlyPayment(double loanAmount, double annualInterestRate, int loanTermInMonths) {
        // Проверка входных параметров
        if (loanAmount <= 0 || annualInterestRate < 0 || loanTermInMonths <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        BigDecimal loan = new BigDecimal(loanAmount);
        BigDecimal monthlyRate = new BigDecimal(annualInterestRate / 12 / 100); // Месячная ставка
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);

        // Нулевая ставка: платеж = сумма / срок
        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal result = loan.divide(new BigDecimal(loanTermInMonths), 2, RoundingMode.HALF_UP);
            System.out.println("Zero rate payment: " + result.doubleValue());
            return result.doubleValue();
        }

        // Формула аннуитета: (r * (1 + r)^n) / ((1 + r)^n - 1)
        BigDecimal power = onePlusRate.pow(loanTermInMonths);
        BigDecimal numerator = monthlyRate.multiply(power);
        BigDecimal denominator = power.subtract(BigDecimal.ONE);
        BigDecimal annuityCoefficient = numerator.divide(denominator, 10, RoundingMode.HALF_UP);
        BigDecimal result = loan.multiply(annuityCoefficient).setScale(2, RoundingMode.HALF_UP);

        // Отладочный вывод
        System.out.println("Calculated monthly payment: " + result.doubleValue());
        return result.doubleValue();
    }

    // Общая сумма к возврату: месячный платеж * срок
    public double calculateTotalAmount(double loanAmount, double annualInterestRate, int loanTermInMonths) {
        double monthlyPayment = calculateMonthlyPayment(loanAmount, annualInterestRate, loanTermInMonths);
        return monthlyPayment * loanTermInMonths;
    }

    // Переплата: общая сумма - сумма кредита
    public double calculateOverpayment(double loanAmount, double annualInterestRate, int loanTermInMonths) {
        double totalAmount = calculateTotalAmount(loanAmount, annualInterestRate, loanTermInMonths);
        return totalAmount - loanAmount;
    }
}