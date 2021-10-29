package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.*;

import com.example.geektrust.datastore.DataStore;
import com.example.geektrust.usecase.UseCaseImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

class UseCaseImplTest {
    DataStore dataStore = new DummyDataStore();
    UseCaseImpl useCase = new UseCaseImpl(dataStore);

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void testSetPortfolio() {
        String command = "CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP UTI_NIFTY_INDEX";
        useCase.setCurrentPortfolio(command);
        assertEquals(3, useCase.getMutualFunds().length);
        assertEquals("AXIS_BLUECHIP", useCase.getMutualFunds()[0]);
    }

    @Test
    public void testCalculateOverlapWithKnownFund() {
        String command1 = "CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP UTI_NIFTY_INDEX";
        useCase.setCurrentPortfolio(command1);

        String command2 = "CALCULATE_OVERLAP MIRAE_ASSET_LARGE_CAP";
        useCase.printFundOverlap(command2);
        assertEquals("MIRAE_ASSET_LARGE_CAP AXIS_BLUECHIP 40.00%\n" +
                "MIRAE_ASSET_LARGE_CAP ICICI_PRU_BLUECHIP 33.33%\n" +
                "MIRAE_ASSET_LARGE_CAP UTI_NIFTY_INDEX 40.00%\n", outputStreamCaptor.toString());
    }

    @Test
    public void testCalculateOverlapWithKnownFundWithZeroOveralap() {
        String command1 = "CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP UTI_NIFTY_INDEX MIRAE_ASSET_EMERGING_BLUECHIP";
        useCase.setCurrentPortfolio(command1);

        String command2 = "CALCULATE_OVERLAP MIRAE_ASSET_LARGE_CAP";
        useCase.printFundOverlap(command2);
        assertEquals("MIRAE_ASSET_LARGE_CAP AXIS_BLUECHIP 40.00%\n" +
                "MIRAE_ASSET_LARGE_CAP ICICI_PRU_BLUECHIP 33.33%\n" +
                "MIRAE_ASSET_LARGE_CAP UTI_NIFTY_INDEX 40.00%\n", outputStreamCaptor.toString());
    }

    @Test
    public void testCalculateOverlapWithUnknownFund() {
        String command1 = "CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP UTI_NIFTY_INDEX";
        useCase.setCurrentPortfolio(command1);

        String command2 = "CALCULATE_OVERLAP UNKNOWN_FUND";
        useCase.printFundOverlap(command2);

        assertEquals("FUND_NOT_FOUND\n", outputStreamCaptor.toString());
    }

    @Test
    public void testAddStockToMutualFund() {
        String command1 = "CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP UTI_NIFTY_INDEX";
        useCase.setCurrentPortfolio(command1);

        String command2 = "CALCULATE_OVERLAP MIRAE_ASSET_LARGE_CAP";

        String command3 = "ADD_STOCK AXIS_BLUECHIP HDFC BANK LIMITED";
        useCase.addStockToMutualFund(command3);
        useCase.printFundOverlap(command2);
        assertEquals("MIRAE_ASSET_LARGE_CAP AXIS_BLUECHIP 33.33%\n" +
                "MIRAE_ASSET_LARGE_CAP ICICI_PRU_BLUECHIP 33.33%\n" +
                "MIRAE_ASSET_LARGE_CAP UTI_NIFTY_INDEX 40.00%\n", outputStreamCaptor.toString());
    }

}

class DummyDataStore implements DataStore {
    Map<String, Set<String>> stockMappings = Map.ofEntries(
            new AbstractMap.SimpleEntry("MIRAE_ASSET_LARGE_CAP", Arrays.asList("HDFC","BHARTI AIRTEL LIMITED", "NMDC LIMITED").stream().collect(Collectors.toSet())),
            new AbstractMap.SimpleEntry("AXIS_BLUECHIP", Arrays.asList("INFOSYS LIMITED",
                    "BHARTI AIRTEL LIMITED").stream().collect(Collectors.toSet())),
            new AbstractMap.SimpleEntry("MIRAE_ASSET_EMERGING_BLUECHIP", Arrays.asList("IPCA LABORATORIES LIMITED",
                    "BATA INDIA LIMITED").stream().collect(Collectors.toSet())),
            new AbstractMap.SimpleEntry("ICICI_PRU_BLUECHIP", Arrays.asList("INFOSYS LIMITED",
                    "BHARTI AIRTEL LIMITED",
                    "OIL & NATURAL GAS CORPORATION LIMITED").stream().collect(Collectors.toSet())),
            new AbstractMap.SimpleEntry("UTI_NIFTY_INDEX", Arrays.asList("BHARTI AIRTEL LIMITED",
                    "EPL LIMITED").stream().collect(Collectors.toSet()))
    );


    @Override
    public Set<String> getStocksInMutualFund(String mutualFund) throws RuntimeException {
        var stockList = this.stockMappings.get(mutualFund);
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