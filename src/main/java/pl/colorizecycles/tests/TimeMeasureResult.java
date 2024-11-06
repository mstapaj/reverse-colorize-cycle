package pl.colorizecycles.tests;

import java.util.List;

import static pl.colorizecycles.utils.UtilsMethods.calculateAverageTime;

public class TimeMeasureResult {
    private final String testedAlgorithm;
    private final double averageTime;
    private final int measuresAmount;
    private final boolean isReverseColorize;
    private int cycleSize;
    private int expectedIteration;
    private boolean reduceLastNumbers;
    private int maxNumber;

    public TimeMeasureResult(String testedAlgorithm, int measuresAmount, List<Double> timeResults, boolean isReverseColorize) {
        this.testedAlgorithm = testedAlgorithm;
        this.averageTime = calculateAverageTime(timeResults);
        this.measuresAmount = measuresAmount;
        this.isReverseColorize = isReverseColorize;
    }

    public TimeMeasureResult(String testedAlgorithm, int measuresAmount, List<Double> timeResults,
                             boolean isReverseColorize, int cycleSize, int expectedIteration, boolean reduceLastNumbers,
                             int maxNumber) {
        this.testedAlgorithm = testedAlgorithm;
        this.averageTime = calculateAverageTime(timeResults);
        this.measuresAmount = measuresAmount;
        this.isReverseColorize = isReverseColorize;
        this.cycleSize = cycleSize;
        this.expectedIteration = expectedIteration;
        this.reduceLastNumbers = reduceLastNumbers;
        this.maxNumber = maxNumber;
    }

    @Override
    public String toString() {
        if (this.isReverseColorize) {
            return "TimeMeasureResult{" + "testedAlgorithm='" + testedAlgorithm + '\'' +
                    ",\naverageTime=" + averageTime +
                    ",\nmeasuresAmount=" + measuresAmount +
                    ",\nisReverseColorize=" + true +
                    ",\ncycleSize=" + cycleSize +
                    ",\nexpectedIteration=" + expectedIteration +
                    ",\nreduceLastNumbers=" + reduceLastNumbers +
                    ",\nmaxNumber=" + maxNumber +
                    '}';
        } else {
            return "TimeMeasureResult{" + "testedAlgorithm='" + testedAlgorithm + '\'' +
                    ",\naverageTime=" + averageTime +
                    ",\nmeasuresAmount=" + measuresAmount +
                    ",\nisReverseColorize=" + false +
                    '}';
        }
    }
}
