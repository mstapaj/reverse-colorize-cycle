package pl.colorizecycles.colorizeMethods;

import pl.colorizecycles.utils.ListGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static pl.colorizecycles.colorizeMethods.Colorize.colorize;

public class ReverseColorizeBruteforceIter implements ReverseColorizeMethod {
    public ColorizeResult reverseColorize(int cycleSize, int expectedIteration, boolean reduceLastNumbers, int maxNumber) {
        Instant start = Instant.now();
        List<List<Integer>> possibleColors = ListGenerator.generatePermutationsIterative(cycleSize, maxNumber);
        for (List<Integer> colors : possibleColors) {
            ColorizeResult colorizeResult = colorize(colors);
            if (colorizeResult.getIteration() == expectedIteration && colorizeResult.isLastColorReduce() == reduceLastNumbers) {
                Instant finish = Instant.now();
                double timeElapsed = (Duration.between(start, finish).toMillis())/1000.0;
                colorizeResult.setReverseColorizeTime(timeElapsed);
                return colorizeResult;
            }
        }
        return new ColorizeResult();
    }
}
