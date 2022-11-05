<<<<<<< HEAD
import complex_expressions.ComplexExpression;
import model.ComplexNumber;
import model.Operation;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private final String[] atoms;

    public ExpressionParser(String[] atoms) {
        this.atoms = atoms;
    }

    private String getOperator() throws Exception {
        List<String> operators = new ArrayList<>();
        for (int i = 1; i < atoms.length; i++)
            if (atoms[i - 1].equals("i") && i < (atoms.length - 1))
                operators.add(atoms[i]);

        if (operators.size() == 0)
            throw new Exception("Invalid expression!");

        String potentialOperator = operators.get(0);
        for (String operator : operators)
            if (!operator.equals(potentialOperator))
                throw new Exception("Invalid expression!");

        return potentialOperator;
    }

    private List<List<String>> getAtomsMatrix() {
        List<List<String>> atomsMatrix = new ArrayList<>();
        List<String> currentComplexNumberAtoms = new ArrayList<>();
        for (int i = 0; i < atoms.length; i++) {
            currentComplexNumberAtoms.add(atoms[i]);
            if (atoms[i].equals("i")) {
                List<String> copyCurrentComplexNumberAtoms = new ArrayList<>(currentComplexNumberAtoms);
                atomsMatrix.add(copyCurrentComplexNumberAtoms);
                currentComplexNumberAtoms.clear();
                i++;
            }
        }
        return atomsMatrix;
    }

    public ComplexExpression parseExpression() throws Exception {
        String operator = getOperator();
        List<List<String>> atomsMatrix = getAtomsMatrix();

        List<ComplexNumber> complexNumbers = new ArrayList<>();

        for (List<String> atomsRow : atomsMatrix) {
            try {
                assert atomsRow.size() == 3 || atomsRow.size() == 5;
                assert atomsRow.get(atomsRow.size() - 1).equals("i");
                boolean negativeImaginaryPart = atomsRow.get(1).equals("-");
                float realPart = Float.parseFloat(atomsRow.get(0));
                float imaginaryPart;
                if (atomsRow.size() == 3)
                    imaginaryPart = 1;
                else
                    imaginaryPart = Float.parseFloat(atomsRow.get(2));
                if (negativeImaginaryPart)
                    imaginaryPart = -imaginaryPart;
                complexNumbers.add(new ComplexNumber(realPart, imaginaryPart));
            } catch (Exception e) {
                throw new Exception("Invalid expression!");
            }
        }

        Operation operation = switch (operator) {
            case "+" -> Operation.ADDITION;
            case "-" -> Operation.SUBTRACTION;
            case "*" -> Operation.MULTIPLICATION;
            case "/" -> Operation.DIVISION;
            default -> throw new Exception("Invalid operand!");
        };

        return ExpressionFactory.getInstance().createExpression(operation, complexNumbers);
    }
}
