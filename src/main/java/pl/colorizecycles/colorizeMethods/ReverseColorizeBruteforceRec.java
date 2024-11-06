package pl.colorizecycles.colorizeMethods;

import pl.colorizecycles.utils.ListGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static pl.colorizecycles.colorizeMethods.Colorize.colorize;

public class ReverseColorizeBruteforceRec implements ReverseColorizeMethod {

    public static List<Integer> generateRandomResultColors(int amountOfProcessors) {
        Random random = new Random();
        List<Integer> colors = new ArrayList<>(Arrays.asList(0, 1, 2));
        List<Integer> result = new ArrayList<>();
        result.add(colors.get(random.nextInt(3)));
        for (int i = 0; i < amountOfProcessors; i++) {
            int randomIndex = random.nextInt(2);
            Integer lastResult = result.get(result.size() - 1);
            colors.remove(lastResult);
            result.add(colors.get(randomIndex));
            colors.add(lastResult);
        }
        return result;
    }

    public static List<String> mapInitColorsToBin(List<Integer> colors) {
        int maxLength = colors.stream()
                .mapToInt(Integer::bitCount)
                .max()
                .orElse(0) + 1;

        return colors.stream()
                .map(color -> String.format("%" + maxLength + "s", Integer.toBinaryString(color))
                        .replace(' ', '0'))
                .collect(Collectors.toList());
    }

    public static List<Integer> generateColors(int amountOfProcessors) {
        List<Integer> result = new ArrayList<>(Collections.nCopies((amountOfProcessors / 3) + 1, Arrays.asList(2, 1, 0))
                .stream()
                .flatMap(Collection::stream)
                .limit(amountOfProcessors)
                .toList());
        if (result.get(result.size() - 1) == 2) {
            result.set(result.size() - 1, 1);
        }
        return result;
    }

    public ColorizeResult reverseColorize(int cycleSize, int expectedIteration, boolean reduceLastNumbers, int maxNumber) {
        Instant start = Instant.now();
        List<List<Integer>> possibleColors = ListGenerator.generatePermutations(cycleSize, maxNumber);
        for (List<Integer> colors : possibleColors) {
            ColorizeResult colorizeResult = colorize(colors);
            if (colorizeResult.getIteration() == expectedIteration && colorizeResult.isLastColorReduce() == reduceLastNumbers) {
                Instant finish = Instant.now();
                double timeElapsed = (Duration.between(start, finish).toMillis()) / 1000.0;
                colorizeResult.setReverseColorizeTime(timeElapsed);
                return colorizeResult;
            }
        }
        return new ColorizeResult();
    }
}
