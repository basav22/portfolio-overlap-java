package com.example.geektrust.dto;

import java.util.ArrayList;

public class APIResponse {
    private ArrayList<MutualFundDetails> funds;

    public ArrayList<MutualFundDetails> getFunds() {
        return funds;
    }

    public void setFunds(ArrayList<MutualFundDetails> funds) {
        this.funds = funds;
    }
}
