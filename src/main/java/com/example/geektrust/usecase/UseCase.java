package com.example.geektrust.usecase;

public interface UseCase {
    void setCurrentPortfolio(String command);

    void printFundOverlap(String command);

    void addStockToMutualFund(String command);
}
