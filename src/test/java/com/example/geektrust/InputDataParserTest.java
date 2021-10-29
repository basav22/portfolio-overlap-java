package com.example.geektrust;

import com.example.geektrust.usecase.UseCase;
import com.example.geektrust.util.InputDataParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class InputDataParserTest {
    DummyUseCaseImpl useCase = new DummyUseCaseImpl();

    InputDataParser inputDataParser = new InputDataParser(useCase);

    @Test
    public void testParseInputFromFile() throws IOException {
        var path = Files.createTempFile("test",".txt");
        String contents = "CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP UTI_NIFTY_INDEX\n" +
                "CALCULATE_OVERLAP MIRAE_ASSET_EMERGING_BLUECHIP\n" +
//                "CALCULATE_OVERLAP MIRAE_ASSET_LARGE_CAP\n" +
                "ADD_STOCK AXIS_BLUECHIP TCS\n"
//                        +
//                "CALCULATE_OVERLAP MIRAE_ASSET_EMERGING_BLUECHIP"
                ;
        Files.write(path, contents.getBytes());
        List<Runnable> lines = inputDataParser.parseInputFromFile(path);
        assertEquals(3, lines.size());
        lines.get(0).run();
        assertEquals("CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP UTI_NIFTY_INDEX", useCase.currentPortfolio);
    }

}

class DummyUseCaseImpl implements UseCase {

    public String currentPortfolio;

    @Override
    public void setCurrentPortfolio(String command) {
        if(command.contains("CURRENT_PORTFOLIO")) {
            this.currentPortfolio = command;
        }
    }

    @Override
    public void printFundOverlap(String command) {
    }

    @Override
    public void addStockToMutualFund(String command) {

    }

}