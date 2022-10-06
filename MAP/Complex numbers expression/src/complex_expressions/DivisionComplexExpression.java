package complex_expressions;

import model.ComplexNumber;
import model.Operation;

public class DivisionComplexExpression extends ComplexExpression {

    public DivisionComplexExpression(ComplexNumber[] complexNumbers) {
        super(Operation.DIVISION, complexNumbers);
    }

    @Override
    public ComplexNumber executeOneOperation(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber) {
        return firstComplexNumber.divide(secondComplexNumber);
    }
}
