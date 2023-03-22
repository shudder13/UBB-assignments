package model;

import helpers.Helpers;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MonomAdderThread extends Thread {
    private final ArrayBlockingQueue<Monom> queue;
    private final List<Monom> resultList;
    private boolean stop = false;

    public MonomAdderThread(ArrayBlockingQueue<Monom> queue, List<Monom> resultList) {
        this.queue = queue;
        this.resultList = resultList;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    public void run() {
        while (!stop || !queue.isEmpty()) {
            Monom monom;
            monom = queue.poll();
            if (monom != null)
                synchronized (resultList) {
                    Helpers.addMonomToList(resultList, monom.getCoefficient(), monom.getExponent());
            }
        }
    }
}
