import constants.Constants;
import helpers.Helpers;
import model.Monom;
import model.MonomAdderThread;
import model.MonomReaderThread;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import static constants.Constants.*;

public class MultithreadedPolynomialAddition {
    public static void main(String[] args) throws InterruptedException {
        List<Monom> resultList = new LinkedList<>();
        ArrayBlockingQueue<Monom> monomQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        List<String> fileNames = new LinkedList<String>(Arrays.asList(Constants.fileNames));

        MonomReaderThread[] monomReaderThreads = new MonomReaderThread[NUMBER_OF_READER_THREADS];
        for (int i = 0; i < NUMBER_OF_READER_THREADS; i++) {
            monomReaderThreads[i] = new MonomReaderThread(fileNames, monomQueue);
            monomReaderThreads[i].start();
        }

        MonomAdderThread[] monomAdderThreads = new MonomAdderThread[NUMBER_OF_THREADS - NUMBER_OF_READER_THREADS];
        for (int i = 0; i < NUMBER_OF_THREADS - NUMBER_OF_READER_THREADS; i++) {
            monomAdderThreads[i] = new MonomAdderThread(monomQueue, resultList);
            monomAdderThreads[i].start();
        }

        for (int i = 0; i < NUMBER_OF_READER_THREADS; i++)
            monomReaderThreads[i].join();
        for (int i = 0; i < NUMBER_OF_THREADS - NUMBER_OF_READER_THREADS; i++)
            monomAdderThreads[i].setStop(true);
        for (int i = 0; i < NUMBER_OF_THREADS - NUMBER_OF_READER_THREADS; i++)
            monomAdderThreads[i].join();

        Helpers.printPolynom(resultList);
    }
}
