package complex_expressions;

import model.ComplexNumber;
import model.Operation;

import java.util.List;

public class MultiplicationComplexExpression extends ComplexExpression {

    public MultiplicationComplexExpression(List<ComplexNumber> complexNumbers) {
        super(Operation.MULTIPLICATION, complexNumbers);
    }

    @Override
    public ComplexNumber executeOneOperation(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber) {
        return firstComplexNumber.multiply(secondComplexNumber);
    }
}
