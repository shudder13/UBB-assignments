package complex_expressions;

import model.ComplexNumber;
import model.Operation;

public class AdditionComplexExpression extends ComplexExpression {

    public AdditionComplexExpression(ComplexNumber[] complexNumbers) {
        super(Operation.ADDITION, complexNumbers);
    }

    @Override
    public ComplexNumber executeOneOperation(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber) {
        return firstComplexNumber.add(secondComplexNumber);
    }
}
