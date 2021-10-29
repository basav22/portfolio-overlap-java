package com.example.geektrust.dto;

import java.util.List;
import java.util.Set;

public class MutualFundDetails {
    private String name;
    private Set<String> stocks;

    public Set<String> getStocks() {
        return stocks;
    }

    public void setStocks(Set<String> stocks) {
        this.stocks = stocks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
