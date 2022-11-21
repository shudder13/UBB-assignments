import model.MyThread;

import java.io.FileNotFoundException;
import java.util.concurrent.CyclicBarrier;

import static utils.Auxs.*;
import static utils.Constants.*;

public class Main {
    private static float[][] sequentialConvolution(float[][] image, float[][] kernel, int imageRows, int imageColumns, int kernelRows, int kernelColumns) {
        float[][] result = new float[imageRows][imageColumns];
        for (int i = 0; i < imageRows; i++)
            for (int j = 0; j < imageColumns; j++) {
                for (int k = -kernelRows / 2; k <= kernelRows / 2; k++)
                    for (int l = -kernelColumns / 2; l <= kernelColumns / 2; l++) {
                        int iAux, jAux;
                        iAux = i + k;
                        jAux = j + l;
                        iAux = Math.max(iAux, 0);
                        iAux = Math.min(iAux, imageRows - 1);
                        jAux = Math.max(jAux, 0);
                        jAux = Math.min(jAux, imageColumns - 1);
                        result[i][j] += kernel[k + kernelRows / 2][l + kernelColumns / 2] * image[iAux][jAux];
                    }
            }
        return result;
    }

    private static void linearConvolution(float[][] image, float[][] kernel, int imageRows, int imageColumns, int kernelRows, int kernelColumns) throws InterruptedException {
        MyThread[] myThreads = new MyThread[NUMBER_OF_THREADS];
        int cellsPerThread = imageRows * imageColumns / NUMBER_OF_THREADS;
        int remainderCellsPerThread = imageRows * imageColumns % NUMBER_OF_THREADS;
        int left = 0, right = cellsPerThread;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            if (remainderCellsPerThread > 0) {
                remainderCellsPerThread--;
                right++;
            }
            myThreads[i] = new MyThread(image, kernel, imageRows, imageColumns, kernelRows, kernelColumns, left, right, cyclicBarrier);
            myThreads[i].start();
            left = right;
            right = right + cellsPerThread;
        }
        for (int i = 0; i < NUMBER_OF_THREADS; i++)
            myThreads[i].join();
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        float[][] image = readMatrix(IMAGE_FILENAME), kernel = readMatrix(KERNEL_FILENAME);
        int imageRows = image.length, imageColumns = image[0].length;
        int kernelRows = kernel.length, kernelColumns = kernel[0].length;

        float[][] imageResultExpected = sequentialConvolution(image, kernel, imageRows, imageColumns, kernelRows, kernelColumns);
        linearConvolution(image, kernel, imageRows, imageColumns, kernelRows, kernelColumns);
        assert2MatricesAreEqual(imageResultExpected, image);
        // printMatrix("Result", image);
    }
}