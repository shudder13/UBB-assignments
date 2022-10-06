package complex_expressions;

import model.ComplexNumber;
import model.Operation;

public class MultiplicationComplexExpression extends ComplexExpression {

    public MultiplicationComplexExpression(ComplexNumber[] complexNumbers) {
        super(Operation.MULTIPLICATION, complexNumbers);
    }

    @Override
    public ComplexNumber executeOneOperation(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber) {
        return firstComplexNumber.multiply(secondComplexNumber);
    }
}
