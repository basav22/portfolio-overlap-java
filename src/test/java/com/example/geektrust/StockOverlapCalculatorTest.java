package com.example.geektrust;

import com.example.geektrust.util.StockOverlapCalculator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StockOverlapCalculatorTest {

    StockOverlapCalculator stockOverlapCalculator = new StockOverlapCalculator();

    @Test
    public void testCalculateOverlap() {
        var fundList1 = Arrays.asList("NAZARA", "BALAJIAMINES", "ALKYLAMINES").stream().collect(Collectors.toSet());
        var fundList2 = Arrays.asList("MAXHEALTH", "BALAJIAMINES", "NAZARA", "TATACOFEE").stream().collect(Collectors.toSet());
        var overlap = stockOverlapCalculator.calculateOverlap(fundList1, fundList2);
        System.out.println(overlap);
        assertEquals("57.14", overlap);
    }
}