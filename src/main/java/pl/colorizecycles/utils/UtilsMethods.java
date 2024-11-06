package pl.colorizecycles.utils;

import java.util.ArrayList;
import java.util.List;

public class UtilsMethods {
    public static List<List<String>> transposeArrayList(List<List<String>> arrayList) {
        if (arrayList.isEmpty() || arrayList.get(0).isEmpty()) {
            return new ArrayList<>();
        }
        int rows = arrayList.size();
        int cols = arrayList.get(0).size();
        List<List<String>> transposed = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            transposed.add(new ArrayList<>());
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed.get(j).add(arrayList.get(i).get(j));
            }
        }
        return transposed;
    }

    public static String[][] convertListListToArray(List<List<String>> list) {
        int rows = list.size();
        String[][] array = new String[rows][];
        for (int i = 0; i < rows; i++) {
            List<String> innerList = list.get(i);
            array[i] = innerList.toArray(new String[0]);
        }

        return array;
    }

    public static Double calculateAverageTime(List<Double> times) {
        return times.stream().reduce(0.0, Double::sum) / times.size();
    }
}
