package pl.colorizecycles.colorizeMethods;

import pl.colorizecycles.utils.ListGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static pl.colorizecycles.colorizeMethods.Colorize.colorize;

public class ReverseColorizeBruteforceRandom implements ReverseColorizeMethod {
    public ColorizeResult reverseColorize(int cycleSize, int expectedIteration, boolean reduceLastNumbers, int maxNumber) {
        Instant start = Instant.now();
        List<List<Integer>> checkedListOfNumbers = new ArrayList<>();
        while (true) {
            List<Integer> colors;
            while (true) {
                colors = ListGenerator.generateRandomList(cycleSize, maxNumber);
                if (!checkedListOfNumbers.contains(colors)) {
                    checkedListOfNumbers.add(colors);
                    break;
                }
            }
            ColorizeResult colorizeResult = colorize(colors);
            if (colorizeResult.getIteration() == expectedIteration && colorizeResult.isLastColorReduce() == reduceLastNumbers) {
                Instant finish = Instant.now();
                double timeElapsed = (Duration.between(start, finish).toMillis()) / 1000.0;
                colorizeResult.setReverseColorizeTime(timeElapsed);
                return colorizeResult;
            }
        }
    }
}
