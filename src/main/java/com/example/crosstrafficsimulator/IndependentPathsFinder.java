package com.example.crosstrafficsimulator;

import java.util.*;

import static com.example.crosstrafficsimulator.ManualSettings.VEHICLE_PATHS;


public class IndependentPathsFinder {

    static boolean intersects(List<Integer> path1, List<Integer> path2) {
        Set<Integer> set1 = new HashSet<>(path1.subList(1, path1.size()));
        for (int i = 1; i < path2.size(); i++) {
            if (set1.contains(path2.get(i))) {
                return true;
            }
        }
        return false;
    }

    static boolean isIndependent(List<Integer> subset,  List<List<Integer>> allVehiclePaths) {
        for (int i = 0; i < subset.size(); i++) {
            for (int j = i + 1; j < subset.size(); j++) {
                List<Integer> path1 = allVehiclePaths.get(subset.get(i));
                List<Integer> path2 = allVehiclePaths.get(subset.get(j));
                if (intersects(path1, path2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static List<List<List<Integer>>> findMaximalIndependentSets(List<List<Integer>> allVehiclePaths) {
        int n = allVehiclePaths.size();
        List<List<Integer>> allIndependent = new ArrayList<>();

        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(i);
                }
            }
            if (isIndependent(subset, allVehiclePaths)) {
                allIndependent.add(new ArrayList<>(subset)); // Zapisujemy niezależny podzbiór
            }
        }

        // Znajdowanie maksymalnych zbiorów niezależnych
        List<List<Integer>> maximal = new ArrayList<>();
        for (List<Integer> subset : allIndependent) {
            boolean isMaximal = true;
            for (List<Integer> other : allIndependent) {
                if (other.size() > subset.size() && other.containsAll(subset)) {
                    isMaximal = false; // Podzbiór nie jest maksymalny
                    break;
                }
            }
            if (isMaximal) {
                maximal.add(subset);
            }
        }

        List<List<List<Integer>>> result = new ArrayList<>();
        // Wyświetlanie wyników
//        System.out.println("Liczba maksymalnych grup: " + maximal.size());
        for (int i = 0; i < maximal.size(); i++) {
//            System.out.println("Grupa " + (i) + ":");
            List<List<Integer>> tmp = new ArrayList<>();
            for (int idx : maximal.get(i)) {
//                System.out.println("  " + VEHICLE_PATHS.get(idx));
                tmp.add(VEHICLE_PATHS.get(idx));
            }
            result.add(tmp);
        }
        return result;
    }


}