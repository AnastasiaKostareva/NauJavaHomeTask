package org.example;

import java.util.ArrayList;
import java.util.Optional;

public class Task2 {
    private int n;
    private ArrayList<Double> numbers;

    public Task2(int n) {
        this.n = n;
        numbers = new ArrayList<Double>(n);
        fillArrRandomNumbers(numbers, -100, 100);
    }

    private void fillArrRandomNumbers(ArrayList<Double> numbers, int min, int max) {
        for (int i = 0; i < this.n; i++) {
            double randomNumber = (Math.random() * (max - min + 1)) + min;
            numbers.add(randomNumber);
        }
    }

    public ArrayList<Double> getSelectionSortArr() {
        ArrayList<Double> newListCopy = new ArrayList<>(numbers);
        for (int i = 0; i < n; i++) {
            double min = Double.MAX_VALUE;
            int ind = i;
            for (int j = i; j < n; j++) {
                var el = newListCopy.get(j);
                if (el < min) {
                    min = el;
                    ind = j;
                }
            }
            var temp = newListCopy.get(i);
            newListCopy.set(i, min);
            newListCopy.set(ind, temp);
        }
        return newListCopy;
    }

    public ArrayList<Double> getArrNumbers() {
        return numbers;
    }
}
