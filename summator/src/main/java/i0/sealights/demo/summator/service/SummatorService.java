package i0.sealights.demo.summator.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class SummatorService {

    private static final MathContext MATH_CTX = new MathContext(2);

    public double sum(BigDecimal first, BigDecimal ratio, int elementsCount) {
        final BigDecimal numerator = BigDecimal.ONE.subtract(ratio.pow(elementsCount));
        final BigDecimal denominator = BigDecimal.ONE.subtract(ratio);

        if (BigDecimal.ZERO.compareTo(denominator) == 0) {
            throw new CalculationException("The ratio can not be 1.0");
        }

        final BigDecimal result = first.multiply(numerator.divide(denominator, RoundingMode.HALF_UP));
        return result.doubleValue();
    }

    public BigDecimal stringToBigDecimal(final String stringValue) {
        try {
            return new BigDecimal(stringValue, MATH_CTX);
        } catch (Exception exception) {
            throw new ValidationException("Invalid value: '" + stringValue +"'");
        }
    }

    public int stringToInt(final String stringValue) {
        try {
            return Integer.parseInt(stringValue);
        } catch (Exception exception) {
            throw new ValidationException("Invalid integer value: '" + stringValue +"'");
        }
    }

}
