#include <iostream>
#include <fstream>
#include <cmath>
#include <chrono>
#include "mpi.h"
using namespace std;

int* readNumber(string filename, int& N) {
	ifstream fin(filename);

	fin >> N;
	int* number = new int[N];

	for (int i = 0; i < N; i++)
		fin >> number[i];

	return number;
}

void writeResult(string filename, int* number, int N) {
	ofstream fout(filename);
	for (int i = N - 1; i >= 0; i--)
		fout << number[i];
}

// SEQUENTIAL IMPLEMENTATION
/*
int main()
{
	int N1, N2;
	auto startTime = chrono::high_resolution_clock::now();
	int* number1 = readNumber("data\\3\\Number1.txt", N1);
	int* number2 = readNumber("data\\3\\Number2.txt", N2);
	int N3 = max(N1, N2) + 1;
	int* result = new int[N3]();

	bool carry = false;
	int sum;
	for (int i = 0; i < N3; i++) {
		sum = 0;
		if (i < N1)
			sum += number1[i];
		if (i < N2)
			sum += number2[i];
		if (carry == true)
			sum += 1;
		if (i == N3 - 1 && carry == false)
			N3--;
		carry = false;

		result[i] = sum % 10;
		if ((sum / 10) == 1)
			carry = true;
	}
	auto finalTime = chrono::high_resolution_clock::now();
	double executionTime = chrono::duration<double, milli>(finalTime - startTime).count();
	cout << "Execution time: " << executionTime;
	
	writeResult("ExpectedResult.txt", result, N3);

	return 0;
}
*/

// VERSION 1
/*
int main(int argc, char* argv[]) {
	MPI_Status status;
	MPI_Init(&argc, &argv);
	int rank, p;
	MPI_Comm_size(MPI_COMM_WORLD, &p);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == 0) {
		auto startTime = chrono::high_resolution_clock::now();
		int start = 0;
		ifstream fin1("Number1.txt");
		ifstream fin2("Number2.txt");
		int N1, N2, N3;
		fin1 >> N1;
		fin2 >> N2;
		N3 = max(N1, N2) + 1;
		int remainder = N3 % (p - 1);
		for (int t = 1; t < p; t++) { // for every process having rank 1 to p - 1
			int auxN = N3 / (p - 1);
			if (t <= remainder)
				auxN++;
			int* segment1 = new int[auxN]();
			int* segment2 = new int[auxN]();
			for (int i = 0; i < auxN; i++) {
				if (!fin1.eof())
					fin1 >> segment1[i];
				if (!fin2.eof())
					fin2 >> segment2[i];
			}
			MPI_Send(&auxN, 1, MPI_INT, t, 0, MPI_COMM_WORLD);
			MPI_Send(segment1, auxN, MPI_INT, t, 0, MPI_COMM_WORLD);
			MPI_Send(segment2, auxN, MPI_INT, t, 0, MPI_COMM_WORLD);
		}
		// am trimis la toate procesele segmentul de numere pe care trebuie sa il calculeze
		// acum urmeaza sa le primesc
		int* result = new int[N3];
		int resultIndex = 0;
		for (int t = 1; t < p; t++) {
			int numberOfElements;
			MPI_Recv(&numberOfElements, 1, MPI_INT, t, 0, MPI_COMM_WORLD, &status);
			MPI_Recv(result + resultIndex, numberOfElements, MPI_INT, t, 0, MPI_COMM_WORLD, &status);
			resultIndex += numberOfElements;
		}
		auto finalTime = chrono::high_resolution_clock::now();
		double executionTime = chrono::duration<double, milli>(finalTime - startTime).count();
		cout << "Execution time: " << executionTime << '\n';
		writeResult("Result.txt", result, resultIndex);
	}
	else {
		int auxN;
		MPI_Recv(&auxN, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		int* segment1 = new int[auxN];
		int* segment2 = new int[auxN];
		MPI_Recv(segment1, auxN, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(segment2, auxN, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		int receivedCarry = 0, sentCarry = 0; // boolean
		if (rank > 1)
			MPI_Recv(&receivedCarry, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);
		// calculus
		int sum;
		bool calculusCarry = false;
		int* resultSegment = new int[auxN]();
		for (int i = 0; i < auxN; i++) {
			sum = 0;
			if (i == 0 && receivedCarry == 1)
				sum += 1;
			sum += segment1[i];
			sum += segment2[i];
			if (calculusCarry == true)
				sum += 1;
			calculusCarry = false;
			resultSegment[i] = sum % 10;
			if ((sum / 10) == 1)
				if (i == (auxN - 1))
					sentCarry = 1;
				else
					calculusCarry = true;
		}
		if ((rank == p - 1) && (resultSegment[auxN - 1] == 0))
			auxN--;
		MPI_Send(&auxN, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		MPI_Send(resultSegment, auxN, MPI_INT, 0, 0, MPI_COMM_WORLD);
		if (rank != (p - 1))
			MPI_Send(&sentCarry, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);
	}
	MPI_Finalize();
	return 0;
}
*/

// VERSION 2
int main(int argc, char* argv[]) {
	MPI_Status status;
	MPI_Init(&argc, &argv);
	int rank, p;
	MPI_Comm_size(MPI_COMM_WORLD, &p);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	
	ifstream fin1("Number1.txt");
	ifstream fin2("Number2.txt");

	int* number1;
	int* number2;
	int* number3;
	int N1, N2, N3;

	fin1 >> N1;
	fin2 >> N2;

	N3 = max(N1, N2) + 1;

	number1 = new int[N3];
	number2 = new int[N3];
	number3 = new int[N3];
	chrono::steady_clock::time_point startTime;

	if (rank == 0) {
		startTime = chrono::high_resolution_clock::now();
		// read number1 and number2 from file
		for (int i = 0; i < N1; i++)
			fin1 >> number1[i];
		for (int i = 0; i < N2; i++)
			fin2 >> number2[i];
		while (N1 < N3)
			number1[N1++] = 0;
		while (N2 < N3)
			number2[N2++] = 0;
	}

	int cat = N3 / p, remainder = N3 % p, start = 0, finish = cat;
	int* displs = new int[p];
	int* offsets = new int[p];
	int maximum_offset = remainder > 0 ? cat + 1 : cat;

	for (int i = 0; i < p; i++) {
		if (remainder) {
			remainder--;
			finish++;
		}
		displs[i] = start;
		offsets[i] = finish - start;

		start = finish;
		finish = start + cat;
	}

	int* auxNumber1 = new int[maximum_offset];
	int* auxNumber2 = new int[maximum_offset];
	int* auxNumber3 = new int[maximum_offset];

	MPI_Scatterv(number1, offsets, displs, MPI_INT, auxNumber1, maximum_offset, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Scatterv(number2, offsets, displs, MPI_INT, auxNumber2, maximum_offset, MPI_INT, 0, MPI_COMM_WORLD);

	int carry = 0;

	if (rank > 0)
		MPI_Recv(&carry, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);

	for (int i = 0; i < offsets[rank]; i++) {
		int sum = auxNumber1[i] + auxNumber2[i] + carry;
		auxNumber3[i] = sum % 10;
		carry = sum / 10;
	}

	if (rank != p - 1)
		MPI_Send(&carry, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);

	MPI_Gatherv(auxNumber3, offsets[rank], MPI_INT, number3, offsets, displs, MPI_INT, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		auto finalTime = chrono::high_resolution_clock::now();
		double executionTime = chrono::duration<double, milli>(finalTime - startTime).count();
		cout << "Execution time: " << executionTime << '\n';
		writeResult("Result.txt", number3, N3);
	}

	MPI_Finalize();
	return 0;
}
