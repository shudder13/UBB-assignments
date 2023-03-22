package helpers;

import model.Monom;

import java.util.List;

public class Helpers {
    public static void addMonomToList(List<Monom> polynom, double coefficient, int exponent) {
        if (coefficient == 0)
            return;

        int pos = 0;
        while (pos < polynom.size() && polynom.get(pos).getExponent() > exponent)
            pos++;

        if (pos < polynom.size() && polynom.get(pos).getExponent() == exponent) {
            polynom.set(pos, new Monom(polynom.get(pos).getCoefficient() + coefficient, exponent));
            if (polynom.get(pos).getCoefficient() == 0)
                polynom.remove(pos);
        }
        else
            polynom.add(pos, new Monom(coefficient, exponent));
    }

    public static void printPolynom(List<Monom> polynom) {
        for (int i = 0; i < polynom.size(); i++) {
            Monom monom = polynom.get(i);
            if (monom.getExponent() == 0)
                System.out.print(monom.getCoefficient());
            else if (monom.getExponent() == 1)
                System.out.print(monom.getCoefficient() + "x");
            else
                System.out.print(monom.getCoefficient() + "x^" + monom.getExponent());
            if (i < polynom.size() - 1)
                System.out.print(" + ");
        }
        System.out.println();
    }
}
