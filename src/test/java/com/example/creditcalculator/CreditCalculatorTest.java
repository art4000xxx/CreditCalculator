package org.example.creditcalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditCalculatorTest {
    private CreditCalculator calculator;
    private static final double DELTA = 1.0; // Допустимая погрешность

    @BeforeEach
    void setUp() {
        calculator = new CreditCalculator();
    }

    // Тест месячного платежа: 100000, 12%, 12 месяцев
    @Test
    void testCalculateMonthlyPayment() {
        double loanAmount = 100000;
        double annualInterestRate = 12.0;
        int loanTermInMonths = 12;
        double expectedMonthlyPayment = 8884.88; // Проверено по формуле аннуитета
        double actual = calculator.calculateMonthlyPayment(loanAmount, annualInterestRate, loanTermInMonths);
        assertEquals(expectedMonthlyPayment, actual, DELTA, "Monthly payment calculation is incorrect");
    }

    // Тест общей суммы: 8884.88 * 12
    @Test
    void testCalculateTotalAmount() {
        double loanAmount = 100000;
        double annualInterestRate = 12.0;
        int loanTermInMonths = 12;
        double expectedTotalAmount = 106618.56;
        double actual = calculator.calculateTotalAmount(loanAmount, annualInterestRate, loanTermInMonths);
        assertEquals(expectedTotalAmount, actual, DELTA, "Total amount calculation is incorrect");
    }

    // Тест переплаты: 106618.56 - 100000
    @Test
    void testCalculateOverpayment() {
        double loanAmount = 100000;
        double annualInterestRate = 12.0;
        int loanTermInMonths = 12;
        double expectedOverpayment = 6618.56;
        double actual = calculator.calculateOverpayment(loanAmount, annualInterestRate, loanTermInMonths);
        assertEquals(expectedOverpayment, actual, DELTA, "Overpayment calculation is incorrect");
    }

    // Тест при нулевой ставке: 100000 / 12
    @Test
    void testCalculateMonthlyPaymentWithZeroInterestRate() {
        double loanAmount = 100000;
        double annualInterestRate = 0.0;
        int loanTermInMonths = 12;
        double expectedMonthlyPayment = 8333.33;
        double actual = calculator.calculateMonthlyPayment(loanAmount, annualInterestRate, loanTermInMonths);
        assertEquals(expectedMonthlyPayment, actual, DELTA, "Monthly payment with zero interest rate is incorrect");
    }

    // Тест на отрицательную сумму
    @Test
    void testCalculateMonthlyPaymentWithInvalidLoanAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateMonthlyPayment(-100000, 12.0, 12);
        }, "Should throw exception for negative loan amount");
    }

    // Тест на отрицательную ставку
    @Test
    void testCalculateMonthlyPaymentWithInvalidInterestRate() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateMonthlyPayment(100000, -12.0, 12);
        }, "Should throw exception for negative interest rate");
    }

    // Тест на нулевой срок
    @Test
    void testCalculateMonthlyPaymentWithInvalidTerm() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateMonthlyPayment(100000, 12.0, 0);
        }, "Should throw exception for zero or negative loan term");
    }
}