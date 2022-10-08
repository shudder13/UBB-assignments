package complex_expressions;

import model.ComplexNumber;
import model.Operation;

import java.util.ArrayList;
import java.util.List;

public abstract class ComplexExpression {
    private final List<ComplexNumber> complexNumbers;

    public ComplexExpression(Operation operation, List<ComplexNumber> complexNumbers) {
        this.complexNumbers = complexNumbers;
    }

    public abstract ComplexNumber executeOneOperation(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber);

    public ComplexNumber execute() {
        ComplexNumber result = complexNumbers.get(0);
        for (int i = 1; i < complexNumbers.size(); i++)
            result = executeOneOperation(result, complexNumbers.get(i));
        return result;
    }
}
