package com.example.geektrust.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StockOverlapCalculator {

    public String calculateOverlap(Set<String> stockList1, Set<String> stockList2) {
        var totalStocksCount = stockList1.size() + stockList2.size();

        // concat lists
        var unionStockList = new ArrayList<String>();
        unionStockList.addAll(stockList1);
        unionStockList.addAll(stockList2);
        // create a set
        var uniqueStockList = new HashSet<String>(unionStockList);
        // find common count
        var commonStockCount = totalStocksCount - uniqueStockList.size();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2);
        var overlap = (200 * (double)commonStockCount / totalStocksCount);
        return df.format(overlap);
    }
}
