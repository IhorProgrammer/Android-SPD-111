package step.learning.android_spd_111;

public class Calculator {
    private double currentValue = 0.0;  // Зберігає поточне значення калькулятора

    public void add(double value) {
        currentValue += value;
    }

    public void subtract(double value) {
        currentValue -= value;
    }

    public void multiply(double value) {
        currentValue *= value;
    }

    public void divide(double value) throws ArithmeticException {
        if (value == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        currentValue /= value;
    }

    public void percent() {
        currentValue /= 100;
    }

    public void power(double exponent) {
        currentValue = Math.pow(currentValue, exponent);
    }

    public void squareRoot() {
        if (currentValue < 0) {
            throw new ArithmeticException("Cannot take the square root of a negative number");
        }
        currentValue = Math.sqrt(currentValue);
    }

    public void removeLastDigit() {
        if(currentValue > 10) {
            String numberStr = this.getCurrentValueString();
            if (numberStr != null && numberStr.length() > 0) {
                numberStr = numberStr.substring(0, numberStr.length() - 1);
            }
            currentValue = Double.parseDouble( numberStr );
        }
        else currentValue = 0;
    }

    public void changeSign() {
        currentValue = -currentValue;
    }

    public void clear() {
        currentValue = 0.0;
    }

    public void inverse() {
        if (currentValue == 0.0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        currentValue = 1.0 / currentValue;
    }

    public String getCurrentValueString() {

        String str = ( currentValue == (int)currentValue ) ? String.valueOf((int)currentValue) : String.valueOf(currentValue);
        if( str.length() > 13 ) {
            str = str.substring(0, 13);
        }
        return str;
    }
    public double getCurrentValue() {
        return  currentValue;
    }

    public void setCurrentValue( double value ) {
        currentValue = value;
    }

    public void setCurrentValueString( String value ) {
        currentValue = Double.parseDouble(value);
    }

}

