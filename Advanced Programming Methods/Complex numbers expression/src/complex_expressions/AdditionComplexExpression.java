package complex_expressions;

import model.ComplexNumber;
import model.Operation;

import java.util.List;

public class AdditionComplexExpression extends ComplexExpression {

    public AdditionComplexExpression(List<ComplexNumber> complexNumbers) {
        super(Operation.ADDITION, complexNumbers);
    }

    @Override
    public ComplexNumber executeOneOperation(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber) {
        return firstComplexNumber.add(secondComplexNumber);
    }
}
