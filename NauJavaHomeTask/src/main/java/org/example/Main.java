package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.printf("Введите n для первого задания: ");
        var n = scanner.nextLine();
        var task1 = new Task1(Integer.parseInt(n));
        Optional<Integer> result1 = task1.findLastPositive();
        System.out.println("Массив: " + java.util.Arrays.toString(task1.getArrNumbers()));
        if (result1.isPresent()) {
            System.out.println("Последний положительный элемент: " + result1.get());
        } else {
            System.out.println("Нет положительных элементов");
        }

        System.out.printf("Введите n для второго задания: ");
        n = scanner.nextLine();
        var task2 = new Task2(Integer.parseInt(n));
        System.out.println("Массив: " + task2.getArrNumbers().toString());
        ArrayList<Double> result2 = task2.getSelectionSortArr();
        System.out.println("Отсортированный массив: " + result2.toString());
        scanner.close();

        var task3 = new Task3();
        System.out.println("Результат преобразования: " + task3.GetDepartmentByName());

        try {
            Task4.getHTTP();
        } catch (IOException e) {
            System.err.println("Ошибка сети: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // восстанавливаем статус прерывания
            System.err.println("Поток прерван");
        }

        var task5 = new Task5();
        task5.start();
        task5.add("first");
        task5.add("second");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Поток был прерван");
        }
        task5.stop();
        task5.add("third");
    }
}