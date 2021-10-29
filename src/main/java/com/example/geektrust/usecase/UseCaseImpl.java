package com.example.geektrust.usecase;

import com.example.geektrust.util.StockOverlapCalculator;
import com.example.geektrust.datastore.DataStore;

import java.util.Arrays;

public class UseCaseImpl implements UseCase{

    private String[] mutualFunds;
    private DataStore stocksDataStore;

    private final StockOverlapCalculator stockOverlapCalculator = new StockOverlapCalculator();

    public UseCaseImpl() {}

    public UseCaseImpl(DataStore stocksDataStore) {
        this.stocksDataStore = stocksDataStore;
    }

    public String[] getMutualFunds() {
        return mutualFunds;
    }

    @Override
    public void setCurrentPortfolio(String command) {
        var args = command.split(" ");
        this.mutualFunds = Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    public void printFundOverlap(String command) {
        var args = getFunctionArguments(command);

        assert args.length == 1;
        var fundToCompare = args[0];

        try {
            Arrays.stream(this.mutualFunds).forEach(fund -> {
                var overlapPercent = stockOverlapCalculator.calculateOverlap(
                        stocksDataStore.getStocksInMutualFund(fund),
                        stocksDataStore.getStocksInMutualFund(fundToCompare)
                );
                if(Double.parseDouble(overlapPercent) > (double)0) {
                    System.out.println(fundToCompare + " " + fund + " " + overlapPercent + "%");
                }
            });
        } catch (RuntimeException exception) {
            if(("FUND_NOT_FOUND").equals(exception.getMessage())) {
                System.out.println(exception.getMessage());
            } else {
                throw exception;
            }
        };
    }

    @Override
    public void addStockToMutualFund(String command) {
        var args = getFunctionArguments(command);
        var fundName = args[0];
        var stockName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        stocksDataStore.addStockToMutualFund(fundName, stockName);
    }

    private String[] getFunctionArguments(String command) {
        var args = command.split(" ");
        return Arrays.copyOfRange(args, 1, args.length);
    }
}
