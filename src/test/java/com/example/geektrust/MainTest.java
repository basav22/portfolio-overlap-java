package com.example.geektrust;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testCreateMonths() {
        List<String> months = Main.createMonths();
        assertNotNull(months);
    }

    @Test
    public void changeGains() {
    }



    @Test
    public void calculatePercent() {
        List<Double> investment = Arrays.asList(1.3,4.5,2.4,8.9);
        Main.calculatePercent(investment,100.9);

    }

    @Test
    public void printBalance() {
        List<Double> investment = Arrays.asList(1.3,4.5,2.4,8.9);
        Map<Integer, List<Double>> portfolio = new HashMap<>();
        portfolio.put(1, investment);
        String balance = Main.printBalance(portfolio,0);
        assertEquals("190", balance);
    }
}