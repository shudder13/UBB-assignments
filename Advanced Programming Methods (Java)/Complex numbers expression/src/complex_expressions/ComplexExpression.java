package complex_expressions;

import model.ComplexNumber;
import model.Operation;

public abstract class ComplexExpression {
    private final ComplexNumber[] complexNumbers;

    public ComplexExpression(Operation operation, ComplexNumber[] complexNumbers) {
        this.complexNumbers = complexNumbers;
    }

    public abstract ComplexNumber executeOneOperation(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber);

    public ComplexNumber execute() {
        ComplexNumber result = complexNumbers[0];
        for (int i = 1; i < complexNumbers.length; i++)
            result = executeOneOperation(result, complexNumbers[i]);
        return result;
    }
}
