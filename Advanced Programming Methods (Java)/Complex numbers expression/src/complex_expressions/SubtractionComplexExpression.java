package complex_expressions;

import model.ComplexNumber;
import model.Operation;

import java.util.List;

public class SubtractionComplexExpression extends ComplexExpression {

    public SubtractionComplexExpression(List<ComplexNumber> complexNumbers) {
        super(Operation.SUBTRACTION, complexNumbers);
    }

    @Override
    public ComplexNumber executeOneOperation(ComplexNumber firstComplexNumber, ComplexNumber secondComplexNumber) {
        return firstComplexNumber.subtract(secondComplexNumber);
    }
}
