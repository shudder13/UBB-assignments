#include <iostream>
#include <fstream>
#include <thread>
#include <cmath>
#include <cassert>
#include "barrier.cpp"
using namespace std;

#define NUMBER_OF_THREADS 16
#define MAX 10000
#define IMAGE_MAX 10000
#define KERNEL_MAX 5
#define IMAGE_FILENAME "data\\3\\image.txt"
#define KERNEL_FILENAME "data\\3\\kernel.txt"

void assert2MatricesAreEqual(float** first, int firstRows, int firstColumns, float** second, int secondRows, int secondColumns) {
	assert(firstRows == secondRows);
	assert(firstColumns == secondColumns);
	for (int i = 0; i < firstRows; i++)
		for (int j = 0; j < firstColumns; j++)
			assert(first[i][j] == second[i][j]);
}

float** readMatrix(string filename, int& rows, int& columns) {
	ifstream fin(filename);
	
	fin >> rows >> columns;
	float** matrix = new float* [rows];
	for (int i = 0; i < rows; i++)
		matrix[i] = new float[columns]();
	
	for (int i = 0; i < rows; i++)
		for (int j = 0; j < columns; j++)
			fin >> matrix[i][j];
	
	return matrix;
}

void printMatrix(string title, float** matrix, int rows, int columns) {
	cout << title << ":\n";
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < columns; j++)
			cout << matrix[i][j] << ' ';
		cout << '\n';
	}
}

float** sequentialConvolution(float** image, float** kernel, int imageRows, int imageColumns, int kernelRows, int kernelColumns) {
	float** result = new float* [imageRows];
	for (int i = 0; i < imageRows; i++)
		result[i] = new float[imageColumns]();

	for (int i = 0; i < imageRows; i++)
		for (int j = 0; j < imageColumns; j++) {
			for (int k = -kernelRows / 2; k <= kernelRows / 2; k++)
				for (int l = -kernelColumns / 2; l <= kernelColumns / 2; l++) {
					int iAux, jAux;
					iAux = i + k;
					jAux = j + l;
					iAux = max(iAux, 0);
					iAux = min(iAux, imageRows - 1);
					jAux = max(jAux, 0);
					jAux = min(jAux, imageColumns - 1);
					result[i][j] += kernel[k + kernelRows / 2][l + kernelColumns / 2] * image[iAux][jAux];
				}
		}

	return result;
}

void linearConvolutionWorker(float** image, float** kernel, int imageRows, int imageColumns, int kernelRows, int kernelColumns, int left, int right, barrier& barrier) {
	int bufferStart, bufferEnd;
	int difference = (imageColumns + 1) * max(kernelRows / 2, kernelColumns / 2);
	bufferStart = max(left - difference, 0);
	bufferEnd = min(right + difference, imageRows * imageColumns - 1);
	float* buffer = new float[bufferEnd - bufferStart + 1];
	for (int i = 0; i <= bufferEnd - bufferStart; i++) {
		int cellIndex = bufferStart + i;
		int rowIndex = cellIndex / imageColumns;
		int columnIndex = cellIndex % imageColumns;
		buffer[i] = image[rowIndex][columnIndex];
	}
	barrier.wait();
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
				iAux = max(iAux, 0);
				iAux = min(iAux, imageRows - 1);
				jAux = max(jAux, 0);
				jAux = min(jAux, imageColumns - 1);
				result += kernel[k + kernelRows / 2][l + kernelColumns / 2] * buffer[iAux * imageColumns + jAux - bufferStart];
			}
		image[i][j] = result;
	}
}

void linearConvolution(float** image, float** kernel, int imageRows, int imageColumns, int kernelRows, int kernelColumns) {
	thread threads[NUMBER_OF_THREADS];
	int cellsPerThread = imageRows * imageColumns / NUMBER_OF_THREADS;
	int remainderCellsPerThread = imageRows * imageColumns % NUMBER_OF_THREADS;
	int left = 0, right = cellsPerThread;
	barrier barrier(NUMBER_OF_THREADS);
	for (int i = 0; i < NUMBER_OF_THREADS; i++) {
		if (remainderCellsPerThread > 0) {
			remainderCellsPerThread--;
			right++;
		}
		threads[i] = thread(linearConvolutionWorker, image, kernel, imageRows, imageColumns, kernelRows, kernelColumns, left, right, ref(barrier));
		left = right;
		right = right + cellsPerThread;
	}
	for (int i = 0; i < NUMBER_OF_THREADS; i++)
		threads[i].join();
}

int main() {
	int imageRows, imageColumns, kernelRows, kernelColumns;
	float** image = readMatrix(IMAGE_FILENAME, imageRows, imageColumns);
	float** kernel = readMatrix(KERNEL_FILENAME, kernelRows, kernelColumns);

	float** imageResultExpected = sequentialConvolution(image, kernel, imageRows, imageColumns, kernelRows, kernelColumns);
	linearConvolution(image, kernel, imageRows, imageColumns, kernelRows, kernelColumns);
	assert2MatricesAreEqual(imageResultExpected, imageRows, imageColumns, image, imageRows, imageColumns);
	// printMatrix("Result", image, imageRows, imageColumns);

	return 0;
}
