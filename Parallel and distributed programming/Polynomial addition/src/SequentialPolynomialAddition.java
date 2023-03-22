import helpers.Helpers;
import model.Monom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import static constants.Constants.fileNames;

public class SequentialPolynomialAddition {
    public static void main(String[] args) throws Exception {
        List<Monom> resultList = new LinkedList<>();

        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    double coefficient = Double.parseDouble(parts[0]);
                    int exponent = Integer.parseInt(parts[1]);

                    Helpers.addMonomToList(resultList, coefficient, exponent);
                }
            } catch (Exception e) {
                throw new Exception("Eroare la citirea fisierului " + fileName);
            }
        }

        Helpers.printPolynom(resultList);
    }
}
