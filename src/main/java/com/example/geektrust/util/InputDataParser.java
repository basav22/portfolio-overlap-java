package com.example.geektrust.util;

import com.example.geektrust.usecase.UseCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InputDataParser {
    private Map<String, Consumer<String>> commandToUseCaseMap = new HashMap();

    public InputDataParser() {}

    public InputDataParser(UseCase useCase) {
        commandToUseCaseMap = Map.ofEntries(
                new AbstractMap.SimpleImmutableEntry<String, Consumer<String>>("CURRENT_PORTFOLIO", useCase::setCurrentPortfolio),
                new AbstractMap.SimpleImmutableEntry<String, Consumer<String>>("CALCULATE_OVERLAP", useCase::printFundOverlap),
                new AbstractMap.SimpleImmutableEntry<String, Consumer<String>>("ADD_STOCK", useCase::addStockToMutualFund)
        );
    }

    public List<Runnable> parseInputFromFile(Path path) throws IOException {
        var lines = Files.readAllLines(path);
        return lines.stream().map(this::convertLineToUseCaseCommand)
                .collect(Collectors.toUnmodifiableList());
    }

    private Runnable convertLineToUseCaseCommand(String line) {
        var commandConsumer = commandToUseCaseMap.get(line.split(" ")[0]);
        return () -> commandConsumer.accept(line);
    }
}
