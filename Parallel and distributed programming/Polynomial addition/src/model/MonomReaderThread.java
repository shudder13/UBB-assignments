package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MonomReaderThread extends Thread {
    private final List<String> fileNames;
    private final ArrayBlockingQueue<Monom> queue;

    public MonomReaderThread(List<String> fileNames, ArrayBlockingQueue<Monom> queue) {
        this.fileNames = fileNames;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            String fileName;
            synchronized (fileNames) {
                if (fileNames.isEmpty())
                    break;
                fileName = fileNames.remove(0);
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    double coefficient = Double.parseDouble(parts[0]);
                    int exponent = Integer.parseInt(parts[1]);

                    queue.add(new Monom(coefficient, exponent));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
