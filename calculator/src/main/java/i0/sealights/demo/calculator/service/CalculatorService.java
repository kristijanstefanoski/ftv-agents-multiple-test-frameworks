package i0.sealights.demo.calculator.service;

import com.fathzer.soft.javaluator.DoubleEvaluator;

public class CalculatorService {

    final ExpressionValidator expressionValidator;

    public CalculatorService(ExpressionValidator expressionValidator) {
        this.expressionValidator = expressionValidator;
    }

    public double eval(final String expression) {
        expressionValidator.validate(expression);
        final DoubleEvaluator doubleEvaluator = new DoubleEvaluator();
        try {
            return doubleEvaluator.evaluate(expression);
        } catch (IllegalArgumentException illegalArgumentException) {
          throw new EvaluationException(expression);
        }
    }
}
