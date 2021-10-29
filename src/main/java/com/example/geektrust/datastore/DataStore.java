package com.example.geektrust.datastore;

import java.util.Set;

public interface DataStore {

    Set<String> getStocksInMutualFund(String mutualFund) throws RuntimeException;

    Set<String> addStockToMutualFund(String mutualFund, String stockName) throws RuntimeException;
}
