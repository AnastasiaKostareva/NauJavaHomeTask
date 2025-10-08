package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class Task5 implements Task{
    private Queue<String> queue = new LinkedList<>();
    private volatile boolean isRun = false;
    private Thread threadProcessing;

    public void add(String element) {
        synchronized (queue) {
            queue.add(element);
            queue.notify();
        }
    }

    @Override
    public void start() {
        if (isRun) {
            return;
        }
        isRun = true;
        threadProcessing = new Thread(() -> {
            while (isRun) {
                String value = null;
                synchronized (queue) {
                    while (isRun && queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    if (!queue.isEmpty()) {
                        value = queue.poll(); // берём элемент
                    }
                }
                if (value != null) {
                    System.out.println(value);
                }
            }
        });
        threadProcessing.start();
    }

    @Override
    public void stop() {
        isRun = false;
        if (threadProcessing != null) {
            synchronized (queue) {
                queue.notify();
            }
            try {
                threadProcessing.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
