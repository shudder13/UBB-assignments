package complex_expressions;

import model.ComplexNumber;
import model.Operation;

import java.util.List;

public class DivisionComplexExpression extends ComplexExpression {

    public DivisionComplexExpression(List<ComplexNumber> complexNumbers) {
        super(Operation.DIVISION, complexNumbers);
    }

    @Override
    public ComplexNumber executeOneOperation(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber) {
        return firstComplexNumber.divide(secondComplexNumber);
    }
}
