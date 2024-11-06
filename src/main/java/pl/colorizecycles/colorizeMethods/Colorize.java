package pl.colorizecycles.colorizeMethods;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pl.colorizecycles.utils.UtilsMethods.transposeArrayList;

public class Colorize {

    public static Boolean checkIfAllBelow6(List<Integer> numbers) {
        for (Integer num : numbers) {
            if (num > 5) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> compareBin(String first, String second) {
        StringBuilder firstStringBuilder = new StringBuilder(first);
        StringBuilder secondStringBuilder = new StringBuilder(second);
        int difference = Math.abs(first.length() - second.length());
        if (first.length() < second.length()) {
            for (int i = 0; i < difference; i++) {
                firstStringBuilder.insert(0, "0");
            }
        }
        if (first.length() > second.length()) {
            for (int i = 0; i < difference; i++) {
                secondStringBuilder.insert(0, "0");
            }
        }
        String firstArray = firstStringBuilder.reverse().toString();
        String secondArray = secondStringBuilder.reverse().toString();

        for (int i = 0; i < firstArray.length(); i++) {
            int bit = Integer.parseInt(String.valueOf(firstArray.charAt(i)));
            try {
                if (!String.valueOf(firstArray.charAt(i)).equals(String.valueOf(secondArray.charAt(i)))) {
                    return Arrays.asList(i, bit, 2 * i + bit);
                }
            } catch (StringIndexOutOfBoundsException exception) {
                return Arrays.asList(i, bit, 2 * i + bit);
            }
        }
        return Arrays.asList(1, 1, 1);
    }

    public static List<Integer> deleteNumber(List<Integer> numbers, Integer numberToDelete) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            int prev = i - 1;
            int next = i + 1;
            if (prev < 0) {
                prev = numbers.size() - 1;
            }
            if (next > numbers.size() - 1) {
                next = 0;
            }
            if (numbers.get(i).equals(numberToDelete)) {
                for (int j = 0; j < 3; j++) {
                    if (!numbers.get(prev).equals(j) && !numbers.get(next).equals(j)) {
                        result.add(j);
                        break;
                    }
                }
            } else {
                result.add(numbers.get(i));
            }
        }
        return result;
    }

    public static ColorizeResult colorize(List<Integer> processors) {
        return colorize(processors, -1);
    }


    public static ColorizeResult colorize(List<Integer> processors, int expectedIterations) {
        Instant start = Instant.now();
        List<List<String>> resultTable = new ArrayList<>();
        List<String> headers = new ArrayList<>(List.of("c"));
        List<Integer> colors = processors;
        resultTable.add(processors.stream().map(String::valueOf).toList());
        int iter = 0;
        while (!checkIfAllBelow6(colors)) {
            List<String> binProcessors = colors
                    .stream().map(Integer::toBinaryString)
                    .toList();
            resultTable.add(binProcessors);
            List<List<Integer>> compareBinResult = new ArrayList<>();
            for (int i = 0; i < binProcessors.size(); i++) {
                if (i + 1 > binProcessors.size() - 1) {
                    compareBinResult.add(compareBin(binProcessors.get(i), binProcessors.get(0)));
                } else {
                    compareBinResult.add(compareBin(binProcessors.get(i), binProcessors.get(i + 1)));
                }
            }
            iter++;
            colors = compareBinResult.stream().map(n -> n.get(2)).toList();
            resultTable.addAll(transposeArrayList(compareBinResult.stream().map(n -> n.stream().map(Object::toString).toList()).toList()));
            headers.addAll(Arrays.asList("bin", "k", "bit", "c" + iter));
            if (expectedIterations > 0 && iter > expectedIterations) {
                return new ColorizeResult();
            }
        }
        List<Integer> colorsBeforeDeletion = new ArrayList<>(colors);
        headers.addAll(Arrays.asList("5", "4", "3"));
        for (int i = 5; i > 2; i--) {
            colors = deleteNumber(colors, i);
            resultTable.add(colors.stream().map(String::valueOf).toList());
        }
        boolean lastColorReduce = !colors.equals(colorsBeforeDeletion);
        Instant finish = Instant.now();
        double timeElapsed = (Duration.between(start, finish).toMillis()) / 1000.0;
        return new ColorizeResult(lastColorReduce, iter, processors, colors, true, resultTable, headers, timeElapsed, 0);
    }


    public static void main(String[] args) {
//        colorize(Arrays.asList(17, 61, 44, 38, 54, 27, 13, 22));
//        colorize(Arrays.asList(77,36,12,62,25,18,9,61));
//        colorize(Arrays.asList(21,67,59,74,78,45,34,37));
        colorize(Arrays.asList(0, 1, 2, 4, 8));
    }
}