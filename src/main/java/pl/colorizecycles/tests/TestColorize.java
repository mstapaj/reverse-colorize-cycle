package pl.colorizecycles.tests;

import pl.colorizecycles.colorizeMethods.*;
import pl.colorizecycles.utils.ListGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pl.colorizecycles.colorizeMethods.Colorize.colorize;

public class TestColorize {
    public static TimeMeasureResult measureAverageTimeColorize(List<Integer> colors, int measuresAmount, String testedAlgorithm) {
        List<Double> times = new ArrayList<>();
        for (int i = 0; i < measuresAmount; i++) {
            ColorizeResult colorizeResult = colorize(colors);
            times.add(colorizeResult.getColorizeTime());
        }
        TimeMeasureResult timeMeasureResult = new TimeMeasureResult(testedAlgorithm, measuresAmount, times, false);
        System.out.println(timeMeasureResult);
        return timeMeasureResult;
    }

    public static TimeMeasureResult measureAverageTimeReverseColorize(ReverseColorizeMethod reverseColorizeMethod, int cycleSize,
                                                                      int expectedIteration, boolean reduceLastNumbers, int maxNumber,
                                                                      int measuresAmount, String testedAlgorithm) {
        List<Double> times = new ArrayList<>();
        for (int i = 0; i < measuresAmount; i++) {
            ColorizeResult colorizeResult = reverseColorizeMethod.reverseColorize(cycleSize, expectedIteration, reduceLastNumbers, maxNumber);
            times.add(colorizeResult.getReverseColorizeTime());
        }
        TimeMeasureResult timeMeasureResult = new TimeMeasureResult(testedAlgorithm, measuresAmount, times, true, cycleSize, expectedIteration,
                reduceLastNumbers, maxNumber);
        System.out.println(timeMeasureResult);
        return timeMeasureResult;
    }

    public static void saveResultsToFile(List<TimeMeasureResult> listOfResults, String fileName) {
        StringBuilder toSaveText = new StringBuilder();
        for (TimeMeasureResult timeMeasureResult : listOfResults) {
            toSaveText.append(timeMeasureResult.toString());
            toSaveText.append("\n\n");
        }
        try (FileWriter writer = new FileWriter("src/main/java/pl/colorizecycles/tests/results/" + fileName)) {
            writer.write(toSaveText.toString());
            System.out.println("Time measure results saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error during saving: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        TimeMeasureResult colorizeResult1 = measureAverageTimeColorize(ListGenerator.generateRandomList(10, 100), 100, "Colorize1");
        TimeMeasureResult colorizeResult2 = measureAverageTimeColorize(ListGenerator.generateRandomList(100, 100), 100, "Colorize2");
        TimeMeasureResult colorizeResult3 = measureAverageTimeColorize(ListGenerator.generateRandomList(1000, 100), 100, "Colorize3");
        TimeMeasureResult colorizeResult4 = measureAverageTimeColorize(ListGenerator.generateRandomList(10000, 100), 100, "Colorize4");
        TimeMeasureResult colorizeResult5 = measureAverageTimeColorize(ListGenerator.generateRandomList(100000, 100), 100, "Colorize5");

        TimeMeasureResult reverseColorizeRecResult1 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRec(), 4, 2, false, 30, 100, "ReverseColorizeBruteforceRec1");
        TimeMeasureResult reverseColorizeRecResult2 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRec(), 4, 2, true, 30, 100, "ReverseColorizeBruteforceRec2");
        TimeMeasureResult reverseColorizeRecResult3 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRec(), 4, 3, false, 30, 100, "ReverseColorizeBruteforceRec3");
        TimeMeasureResult reverseColorizeRecResult4 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRec(), 4, 3, true, 30, 100, "ReverseColorizeBruteforceRec4");

        TimeMeasureResult reverseColorizeIterResult1 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceIter(), 4, 2, false, 30, 100, "ReverseColorizeBruteforceIter1");
        TimeMeasureResult reverseColorizeIterResult2 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceIter(), 4, 2, true, 30, 100, "ReverseColorizeBruteforceIter2");
        TimeMeasureResult reverseColorizeIterResult3 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceIter(), 4, 3, false, 30, 100, "ReverseColorizeBruteforceIter3");
        TimeMeasureResult reverseColorizeIterResult4 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceIter(), 4, 3, true, 30, 100, "ReverseColorizeBruteforceIter4");

        TimeMeasureResult reverseColorizeRandomResult1 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRandom(), 4, 2, false, 30, 100, "ReverseColorizeBruteforceRandom1");
        TimeMeasureResult reverseColorizeRandomResult2 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRandom(), 4, 2, true, 30, 100, "ReverseColorizeBruteforceRandom2");
        TimeMeasureResult reverseColorizeRandomResult3 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRandom(), 4, 3, false, 30, 100, "ReverseColorizeBruteforceRandom3");
        TimeMeasureResult reverseColorizeRandomResult4 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRandom(), 4, 3, true, 30, 100, "ReverseColorizeBruteforceRandom4");

        TimeMeasureResult reverseColorizeRandomResultBigCycles1 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRandom(), 10, 3, false, 100, 100, "ReverseColorizeBruteforceRandomBigCycles1");
        TimeMeasureResult reverseColorizeRandomResultBigCycles2 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRandom(), 20, 3, false, 100, 100, "ReverseColorizeBruteforceRandomBigCycles2");
        TimeMeasureResult reverseColorizeRandomResultBigCycles3 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRandom(), 30, 3, false, 100, 100, "ReverseColorizeBruteforceRandomBigCycles3");

        TimeMeasureResult reverseColorizeRandomResultExpIter1 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRandom(), 100, 2, true, 10000, 100, "ReverseColorizeBruteforceRandomExpIter1");
        TimeMeasureResult reverseColorizeRandomResultExpIter2 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRandom(), 100, 3, true, 10000, 100, "ReverseColorizeBruteforceRandomExpIter2");
        TimeMeasureResult reverseColorizeRandomResultExpIter3 = measureAverageTimeReverseColorize(new ReverseColorizeBruteforceRandom(), 100, 4, true, 10000, 100, "ReverseColorizeBruteforceRandomExpIter3");


        saveResultsToFile(Arrays.asList(colorizeResult1, colorizeResult2, colorizeResult3, colorizeResult4, colorizeResult5), "testMeasureResultsColorize.txt");
        saveResultsToFile(Arrays.asList(reverseColorizeRecResult1, reverseColorizeRecResult2, reverseColorizeRecResult3, reverseColorizeRecResult4), "testMeasureResultsReverseColorizeRec.txt");
        saveResultsToFile(Arrays.asList(reverseColorizeIterResult1, reverseColorizeIterResult2, reverseColorizeIterResult3, reverseColorizeIterResult4), "testMeasureResultsReverseColorizeIter.txt");
        saveResultsToFile(Arrays.asList(reverseColorizeRandomResult1, reverseColorizeRandomResult2, reverseColorizeRandomResult3, reverseColorizeRandomResult4), "testMeasureResultsReverseColorizeRandom.txt");
        saveResultsToFile(Arrays.asList(reverseColorizeRandomResultBigCycles1, reverseColorizeRandomResultBigCycles2, reverseColorizeRandomResultBigCycles3), "testMeasureResultsReverseColorizeRandomBigCycles.txt");
        saveResultsToFile(Arrays.asList(reverseColorizeRandomResultExpIter1, reverseColorizeRandomResultExpIter2, reverseColorizeRandomResultExpIter3), "testMeasureResultsReverseColorizeRandomExpIter.txt");
    }
}
