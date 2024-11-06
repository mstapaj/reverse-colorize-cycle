package pl.colorizecycles.colorizeMethods;

import java.util.List;

public class ColorizeResult {
    private final boolean lastColorReduce;
    private final int iteration;
    private final List<Integer> initColors;
    private final List<Integer> finalColors;
    private final boolean isSuccessful;
    private final List<List<String>> preparedTableData;
    private final List<String> preparedHeaders;
    private final double colorizeTime;
    private double reverseColorizeTime;

    public ColorizeResult() {
        this.lastColorReduce = false;
        this.iteration = 0;
        this.initColors = null;
        this.finalColors = null;
        this.isSuccessful = false;
        this.preparedTableData = null;
        this.preparedHeaders = null;
        this.colorizeTime = 0;
        this.reverseColorizeTime = 0;
    }

    public ColorizeResult(boolean lastColorReduce, int iteration, List<Integer> initColors, List<Integer> finalColors,
                          boolean isSuccessful, List<List<String>> preparedTableData, List<String> preparedHeaders,
                          double colorizeTime, double reverseColorizeTime) {
        this.lastColorReduce = lastColorReduce;
        this.iteration = iteration;
        this.initColors = initColors;
        this.finalColors = finalColors;
        this.isSuccessful = isSuccessful;
        this.preparedTableData = preparedTableData;
        this.preparedHeaders = preparedHeaders;
        this.colorizeTime = colorizeTime;
        this.reverseColorizeTime = reverseColorizeTime;
    }

    public boolean isLastColorReduce() {
        return lastColorReduce;
    }

    public int getIteration() {
        return iteration;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public List<List<String>> getPreparedTableData() {
        return preparedTableData;
    }

    public List<String> getPreparedHeaders() {
        return preparedHeaders;
    }

    public double getColorizeTime() {
        return colorizeTime;
    }

    public double getReverseColorizeTime() {
        return reverseColorizeTime;
    }

    public void setReverseColorizeTime(double reverseColorizeTime) {
        this.reverseColorizeTime = reverseColorizeTime;
    }

    @Override
    public String toString() {
        return "ColorizeResult{" +
                "lastColorReduce=" + lastColorReduce +
                ", iteration=" + iteration +
                ", initColors=" + initColors +
                ", finalColors=" + finalColors +
                ", isSuccessful=" + isSuccessful +
                ", preparedTableData=" + preparedTableData +
                ", preparedHeaders=" + preparedHeaders +
                ", colorizeTime=" + colorizeTime +
                ", reverseColorizeTime=" + reverseColorizeTime +
                '}';
    }
}
