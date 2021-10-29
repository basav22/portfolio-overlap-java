package com.example.geektrust;

import com.example.geektrust.datastore.StocksDataStore;
import com.example.geektrust.usecase.UseCaseImpl;
import com.example.geektrust.util.InputDataParser;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        StocksDataStore stocksDataStore = new StocksDataStore("https://geektrust.s3.ap-southeast-1.amazonaws.com/portfolio-overlap/stock_data.json");
        UseCaseImpl useCase = new UseCaseImpl(stocksDataStore);
        InputDataParser inputDataParser = new InputDataParser(useCase);

        var inputFilePath = Paths.get(args[0]);
        var commands = inputDataParser.parseInputFromFile(inputFilePath);

        commands.forEach(Runnable::run);
	}
}
