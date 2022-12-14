import complex_expressions.*;
import model.ComplexNumber;
import model.Operation;

import java.util.List;

public class ExpressionFactory {
    private final static ExpressionFactory instance = new ExpressionFactory();

    private ExpressionFactory() {}

    public static ExpressionFactory getInstance() {
        return instance;
    }

    public ComplexExpression createExpression(Operation operation, List<ComplexNumber> complexNumbers) {
        return switch (operation) {
            case ADDITION -> new AdditionComplexExpression(complexNumbers);
            case SUBTRACTION -> new SubtractionComplexExpression(complexNumbers);
            case MULTIPLICATION -> new MultiplicationComplexExpression(complexNumbers);
            case DIVISION -> new DivisionComplexExpression(complexNumbers);
        };
    }
}
