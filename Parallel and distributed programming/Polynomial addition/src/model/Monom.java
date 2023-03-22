package model;

public class Monom {
    private double coefficient;
    private int exponent;

    public Monom(double coefficient, int exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public int getExponent() {
        return exponent;
    }
}
