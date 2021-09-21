package com.example.geektrust;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static double[] portfolioPercent = new double[3];
    int count = 0;

    enum Command  {
        ALLOCATE,
        SIP,
        CHANGE,
        BALANCE,
        REBALANCE
    }

    static List<String> createMonths() {
        List<String> months = new LinkedList<>();

        months.add("JANUARY");
        months.add("FEBRUARY");
        months.add("MARCH");
        months.add("APRIL");
        months.add("MAY");
        months.add("JUNE");
        months.add("JULY");
        months.add("AUGUST");
        months.add("SEPTEMBER");
        months.add("OCTOBER");
        months.add("NOVEMBER");
        months.add("DECEMBER");

        return months;
    }

    public static void main(String[] args) {
        Map<Integer, List<Double>> portfolio = new LinkedHashMap<>();

        List<Double> sip = new LinkedList<>();
        List<Double> updatedInvestment = new LinkedList<>();
        List<Double> investment = new LinkedList<>();

        List<String> months = createMonths();

        int count = 0;

        try (Stream<String> fileLines = Files.lines(new File(args[0]).toPath())) {
            List<String> lines = fileLines.map(String::trim).filter(s -> !s.matches(" ")).collect(Collectors.toList());

            for (String line : lines) {
                String[] instructions = line.trim().split(" ");

                Command command = Command.valueOf(instructions[0]);

                switch (command) {
                    case ALLOCATE:
                        count = allocateMoney(portfolio, investment, count, instructions);
                        break;
                    case SIP:
                        for (int i = 1; i < instructions.length; i++) {
                            sip.add(Double.parseDouble(instructions[i]));
                        }
                        break;
                    case CHANGE:
                        count = changeGains(portfolio, sip, instructions, count);
                        break;
                    case BALANCE:
                        printBalance(portfolio, months.indexOf(instructions[1]));
                        break;
                    case REBALANCE:
                        int size = portfolio.size() - 1;
                        if (size % 6 == 0) {
                            printRebalance(portfolio, updatedInvestment, count);
                        } else {
                            System.out.println("CANNOT_REBALANCE");
                        }
                        break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int changeGains(Map<Integer, List<Double>> portfolio, List<Double> sip, String[] instructions, int count) {
        Pattern p = Pattern.compile("^-?\\d+\\.?\\d+");
        List<Double> listValues = portfolio.get(count - 1);

        List<Double> updatedInvestment = new LinkedList<>();

        double total = 0;

        for (int i = 1; i < instructions.length - 1; i++) {
            Matcher m = p.matcher(instructions[i]);
            if (m.find()) {
                double value = Double.parseDouble(m.group());

                double temp = listValues.get(i - 1);

                if (count - 1 > 0) {
                    double s1 = temp + sip.get(i - 1);
                    double s2 = s1 * value;
                    double s3 = s2 / 100;
                    double s4 = s3 + s1;
                    updatedInvestment.add(s4);
                    total += s4;
                } else {
                    double a1 = temp * value;
                    double a2 = a1 / 100;
                    double a3 = a2 + temp;
                    updatedInvestment.add(a3);
                    total += a3;
                }
            }
        }
        updatedInvestment.add(total);
        portfolio.put(count, updatedInvestment);

        count++;

        return count;
    }

    public static int allocateMoney(Map<Integer, List<Double>> portfolio, List<Double> investment, int count,
            String[] instructions) {
        double total;
        double temp;
        total = 0;

        for (int i = 1; i < instructions.length; i++) {
            temp = Double.parseDouble(instructions[i]);
            total += temp;
            investment.add(temp);
        }
        investment.add(total);

        portfolio.put(count, investment);

        calculatePercent(investment, total);

        count++;
        return count;
    }

    public static void calculatePercent(List<Double> investment, double total) {
        for (int i = 0; i < investment.size()-1; i++) {
            portfolioPercent[i] = investment.get(i) / total;
        }
    }

    public static void printRebalance(Map<Integer, List<Double>> portfolio, List<Double> updatedInvestment, int count) {
        double total;
        List<Double> listValues;
        Double printValue;
        StringBuilder sb = new StringBuilder();

        listValues = portfolio.get(count - 1);

        total = listValues.get(listValues.size() - 1);

        for (double d : portfolioPercent) {
            updatedInvestment.add(d * total);
            printValue = d * total;
            sb.append(printValue.shortValue());
            sb.append(" ");
        }

        updatedInvestment.add(total);

        portfolio.put(count - 1, updatedInvestment);

        System.out.println(sb);
    }

    public static String printBalance(Map<Integer, List<Double>> portfolio, int index) {
        List<Double> monthlyValues = portfolio.get(index + 1);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < monthlyValues.size() - 1; i++) {
           sb.append(monthlyValues.get(i).shortValue());
            sb.append(" ");
        }
        System.out.println(sb);
        return sb.toString();
    }
}
