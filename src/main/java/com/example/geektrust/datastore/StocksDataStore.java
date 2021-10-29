package com.example.geektrust.datastore;

import com.example.geektrust.dto.APIResponse;
import com.example.geektrust.dto.MutualFundDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class StocksDataStore implements DataStore{
    private final String dataUrl;
    private Map<String, Set<String>> mutualFundStockMap;

    public StocksDataStore(String url) {
        this.dataUrl = url;
        this.initialise();
    }

    private void initialise() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            URL url = new URL(this.dataUrl);
            var apiResponse = objectMapper.readValue(url, APIResponse.class);

            this.mutualFundStockMap = apiResponse.getFunds().stream()
                    .collect(Collectors.toMap(MutualFundDetails::getName, MutualFundDetails::getStocks));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Set<String>> getMutualFundStockMap() {
        return mutualFundStockMap;
    }

    @Override
    public Set<String> getStocksInMutualFund(String mutualFund) throws RuntimeException {
        var stockList = this.mutualFundStockMap.get(mutualFund);
        if(Objects.isNull(stockList)) {
          throw new RuntimeException("FUND_NOT_FOUND");
        }
        return stockList;
    }

    @Override
    public Set<String> addStockToMutualFund(String mutualFund, String stockName) throws RuntimeException {
        var currentStocks = getStocksInMutualFund(mutualFund);
        currentStocks.add(stockName);
        return currentStocks;
    }
}
