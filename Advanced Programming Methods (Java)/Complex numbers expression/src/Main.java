import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the expression: ");
        String rawExpression = scanner.nextLine();
        String[] atoms = rawExpression.split(" ");
    }
}
