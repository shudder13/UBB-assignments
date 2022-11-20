package model;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MyThread extends Thread {
    private final float[][] image, kernel;
    private final int imageRows, imageColumns, kernelRows, kernelColumns, left, right;
    private final CyclicBarrier cyclicBarrier;

    public MyThread(float[][] image, float[][] kernel, int imageRows, int imageColumns, int kernelRows, int kernelColumns, int left, int right, CyclicBarrier cyclicBarrier) {
        this.image = image;
        this.kernel = kernel;
        this.imageRows = imageRows;
        this.imageColumns = imageColumns;
        this.kernelRows = kernelRows;
        this.kernelColumns = kernelColumns;
        this.left = left;
        this.right = right;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        int bufferStart, bufferEnd;
        int difference = (imageColumns + 1) * Math.max(kernelRows / 2, kernelColumns / 2);
        bufferStart = Math.max(left - difference, 0);
        bufferEnd = Math.min(right + difference, imageRows * imageColumns - 1);
        float[] buffer = new float[bufferEnd - bufferStart + 1];
        for (int i = 0; i <= bufferEnd - bufferStart; i++) {
            int cellIndex = bufferStart + i;
            int rowIndex = cellIndex / imageColumns;
            int columnIndex = cellIndex % imageColumns;
            buffer[i] = image[rowIndex][columnIndex];
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        int i, j;
        for (int t = left; t < right; t++) {
            i = t / imageColumns;
            j = t % imageColumns;
            float result = 0;
            for (int k = -kernelRows / 2; k <= kernelRows / 2; k++)
                for (int l = -kernelColumns / 2; l <= kernelColumns / 2; l++) {
                    int iAux, jAux;
                    iAux = i + k;
                    jAux = j + l;
                    iAux = Math.max(iAux, 0);
                    iAux = Math.min(iAux, imageRows - 1);
                    jAux = Math.max(jAux, 0);
                    jAux = Math.min(jAux, imageColumns - 1);
                    result += kernel[k + kernelRows / 2][l + kernelColumns / 2] * buffer[iAux * imageColumns + jAux - bufferStart];
                }
            image[i][j] = result;
        }
    }
}
