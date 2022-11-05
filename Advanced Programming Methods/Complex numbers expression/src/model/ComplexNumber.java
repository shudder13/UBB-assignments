package model;

public class ComplexNumber {
    private final float realPart;
    private final float imaginaryPart;

    public ComplexNumber(float realPart, float imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public float getRealPart() {
        return realPart;
    }

    public float getImaginaryPart() {
        return imaginaryPart;
    }

    public ComplexNumber add(ComplexNumber other) {
        float newRealPart = this.getRealPart() + other.getRealPart();
        float newImaginaryPart = this.getImaginaryPart() + other.getImaginaryPart();
        return new ComplexNumber(newRealPart, newImaginaryPart);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        float newRealPart = this.getRealPart() - other.getRealPart();
        float newImaginaryPart = this.getImaginaryPart() - other.getImaginaryPart();
        return new ComplexNumber(newRealPart, newImaginaryPart);
    }

    public ComplexNumber multiply(ComplexNumber other) {
        // this = a + b * i
        // other = c + d * i
        float a = this.getRealPart();
        float b = this.getImaginaryPart();
        float c = other.getRealPart();
        float d = other.getImaginaryPart();
        float newRealPart = a * c - b * d;
        float newImaginaryPart = a * d + b * c;
        return new ComplexNumber(newRealPart, newImaginaryPart);
    }

    public ComplexNumber divide(ComplexNumber other) {
        // this = a + b * i
        // other = c + d * i
        float a = this.getRealPart();
        float b = this.getImaginaryPart();
        float c = other.getRealPart();
        float d = other.getImaginaryPart();
        float newRealPart = (a * c + b * d) / (c * c + d * d);
        float newImaginaryPart = (a * c + b * d) / (c * c + d * d);
        return new ComplexNumber(newRealPart, newImaginaryPart);
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(this.getRealPart(), -this.getImaginaryPart());
    }

    @Override
    public String toString() {
        if (imaginaryPart >= 0)
            return realPart + " + " + imaginaryPart + " * i";
        else
            return realPart + " - " + -imaginaryPart + " * i";
    }
}
