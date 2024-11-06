package pl.colorizecycles.utils;

import java.util.*;

public class ListGenerator {
    public static List<List<Integer>> generatePermutations(int n, int k) {
        k++;
        List<List<Integer>> resultList = new ArrayList<>();
        List<Integer> currentList = new ArrayList<>();
        boolean[] used = new boolean[k];

        generatePermutationsHelper(resultList, currentList, used, n, k);
        return resultList;
    }

    private static void generatePermutationsHelper(List<List<Integer>> resultList, List<Integer> currentList, boolean[] used, int n, int k) {
        if (currentList.size() == n) {
            resultList.add(new ArrayList<>(currentList));
            return;
        }

        for (int i = 0; i < k; i++) {
            if (!used[i]) {
                currentList.add(i);
                used[i] = true;

                generatePermutationsHelper(resultList, currentList, used, n, k);

                currentList.remove(currentList.size() - 1);
                used[i] = false;
            }
        }
    }

    public static List<List<Integer>> generatePermutationsIterative(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        int[] current = new int[n];
        while (true) {
            List<Integer> permutation = new ArrayList<>();
            for (int value : current) {
                permutation.add(value);
            }
            if (hasNoDuplicates(permutation)) {
                result.add(permutation);
            }
            int index = n - 1;
            while (index >= 0 && current[index] == k) {
                index--;
            }
            if (index < 0) {
                break;
            }
            current[index]++;
            for (int i = index + 1; i < n; i++) {
                current[i] = 0;
            }
        }

        return result;
    }

    private static boolean hasNoDuplicates(List<Integer> list) {
        Set<Integer> set = new HashSet<>(list);
        return set.size() == list.size();
    }

    public static List<Integer> generateRandomList(int n, int k) {
        Random random = new Random();
        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            randomList.add(random.nextInt(k + 1));
        }
        return randomList;
    }

    public static void main(String[] args) {
        int n = 3; // ilość list w wynikowej liście list
        int k = 10; // maksymalna liczba w podlistach

        List<List<Integer>> generatedLists = generatePermutations(n, k);
        for (List<Integer> list : generatedLists) {
            System.out.println(list);
        }
    }
}
