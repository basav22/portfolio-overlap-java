# What is this
This uses external API to calculate **Portfolio Overlap** with given new fund on your existing fund.

Refer to details of Input/ Output in **Geektrust Coding Chanllenge** [here](https://www.geektrust.in/coding-problem/backend/portfolio-overlap)

Sample Input - 
```
CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP UTI_NIFTY_INDEX  -> Command to Set CURRENT_PORTFOLIO
CALCULATE_OVERLAP MIRAE_ASSET_EMERGING_BLUECHIP -> Command to Calculate Overlap with below formula
ADD_STOCK AXIS_BLUECHIP TCS -> Command to ADD stock to Mutual Fund as rebalancing
```
Output
```

ICICI_PRU_NIFTY_NEXT_50_INDEX UTI_NIFTY_INDEX 20.37%
ICICI_PRU_NIFTY_NEXT_50_INDEX AXIS_MIDCAP 14.81%
ICICI_PRU_NIFTY_NEXT_50_INDEX PARAG_PARIKH_FLEXI_CAP 7.41%
FUND_NOT_FOUND
ICICI_PRU_NIFTY_NEXT_50_INDEX UTI_NIFTY_INDEX 20.37%
ICICI_PRU_NIFTY_NEXT_50_INDEX AXIS_MIDCAP 14.68%
ICICI_PRU_NIFTY_NEXT_50_INDEX PARAG_PARIKH_FLEXI_CAP 7.32%

```

# Formula 

`Overlap (A,B) = 2*(No of common stocks in A & B)/ (No of stocks in A + No of stocks in B) * 100`

# Pre-requisites
* Java 1.8/1.11/1.15
* Gradle 6

# How to run the code

We have provided scripts to execute the code. 

Use `run.sh` if you are Linux/Unix/macOS Operating systems and `run.bat` if you are on Windows.

Internally both the scripts run the following commands 

 * `gradle clean build -x test --no-daemon` - This will create a jar file `geektrust.jar` in the `build/libs` folder.
 * `java -jar build/libs/geektrust.jar sample_input/input1.txt` - This will execute the jar file passing in the sample input file as the command line argument

 Use the build.gradle file provided along with this project. Please change the main class entry under the `jar` task

 ```
 manifest {
        attributes 'Main-Class' : 'com.example.geektrust.Main' //Change this to the main class of your program which will be executed
    }
```
in the build.gradle if your main class has changed.

 # How to execute the unit tests

 `gradle clean test --no-daemon` will execute the unit test cases.

# Help

You can refer our help documents [here](https://help.geektrust.in)
You can read build instructions [here](https://github.com/geektrust/coding-problem-artefacts/tree/master/Java)
