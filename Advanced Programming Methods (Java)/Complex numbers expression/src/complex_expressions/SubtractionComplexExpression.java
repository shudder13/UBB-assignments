package complex_expressions;

import model.ComplexNumber;
import model.Operation;

public class SubtractionComplexExpression extends ComplexExpression {

    public SubtractionComplexExpression(ComplexNumber[] complexNumbers) {
        super(Operation.SUBTRACTION, complexNumbers);
    }

    @Override
    public ComplexNumber executeOneOperation(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber) {
        return firstComplexNumber.subtract(secondComplexNumber);
    }
}
