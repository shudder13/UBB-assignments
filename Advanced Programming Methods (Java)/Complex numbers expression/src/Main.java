import complex_expressions.ComplexExpression;
import model.ComplexNumber;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the expression: ");
        String rawExpression = scanner.nextLine();
        String[] atoms = rawExpression.split(" ");
        try {
            ExpressionParser expressionParser = new ExpressionParser(atoms);
            ComplexExpression complexExpression = expressionParser.parseExpression();
            ComplexNumber result = complexExpression.execute();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
