package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Auxs {
    public static float[][] readMatrix(String matrixFileName) throws FileNotFoundException {
        File file = new File(matrixFileName);
        Scanner scanner = new Scanner(file);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        float[][] f = new float[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                f[i][j] = scanner.nextFloat();
        return f;
    }

    public static void printMatrix(String title, float[][] matrix) {
        System.out.println(title + ":");
        for (float[] row : matrix) {
            for (float cell : row)
                System.out.print(cell + " ");
            System.out.println();
        }
    }

    public static void assert2MatricesAreEqual(float[][] first, float[][] second) {
        assert first.length == second.length;
        assert first[0].length == second[0].length;
        for (int i = 0; i < first.length; i++)
            for (int j = 0; j < first[0].length; j++)
                assert first[i][j] == second[i][j];
    }
}
