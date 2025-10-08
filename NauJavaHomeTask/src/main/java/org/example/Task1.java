package org.example;

import java.util.Optional;

public class Task1 {
    private int n;
    private int[] numbers;

    public Task1(int n) {
        this.n = n;
        numbers = new int[n];
        fillArrRandomNumbers(numbers, -100, 100);
    }

    private void fillArrRandomNumbers(int[] numbers, int min, int max) {
        for (int i = 0; i < this.n; i++) {
            int randomNumber = (int) (Math.random() * (max - min + 1)) + min;
            numbers[i] = randomNumber;
        }
    }

    public Optional<Integer> findLastPositive() {
        for (int i = this.n - 1; i >= 0; i--) {
            if (numbers[i] > 0) {
                return Optional.of(numbers[i]);
            }
        }
        return Optional.empty();
    }

    public int[] getArrNumbers() {
        return numbers;
    }
}
