package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.*;

import com.example.geektrust.datastore.StocksDataStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class StocksDataStoreTest {
    StocksDataStore stocksDataStore = new StocksDataStore("https://geektrust.s3.ap-southeast-1.amazonaws.com/portfolio-overlap/stock_data.json");

    @Test
    public void testStocksData() throws IOException {
        assertEquals(10, stocksDataStore.getMutualFundStockMap().keySet().size());
        assertEquals(51, stocksDataStore.getStocksInMutualFund("ICICI_PRU_NIFTY_NEXT_50_INDEX").size());
        assertTrue(stocksDataStore.getStocksInMutualFund("ICICI_PRU_NIFTY_NEXT_50_INDEX")
                .contains("INDRAPRASTHA GAS LIMITED"));
    }

    @Test
    public void handleGetStocksInUnknownMutualFund() {
        assertThrows(RuntimeException.class, () -> {
            stocksDataStore.getStocksInMutualFund("UNKNOWN_FUND");
        });
    }

    @Test
    public void testAddStockToMutualFund() {
        stocksDataStore.addStockToMutualFund("ICICI_PRU_NIFTY_NEXT_50_INDEX", "HDFC BANK");
        assertEquals(52, stocksDataStore.getStocksInMutualFund("ICICI_PRU_NIFTY_NEXT_50_INDEX").size());

        stocksDataStore.addStockToMutualFund("ICICI_PRU_NIFTY_NEXT_50_INDEX", "HDFC BANK");
        assertEquals(52, stocksDataStore.getStocksInMutualFund("ICICI_PRU_NIFTY_NEXT_50_INDEX").size());
    }

    @Test
    public void testAddStockToUnknownMutualFund() {
        assertThrows(RuntimeException.class, () -> {
            stocksDataStore.addStockToMutualFund("UNKNOWN_FUND", "HDFC BANK");
        });

    }


}