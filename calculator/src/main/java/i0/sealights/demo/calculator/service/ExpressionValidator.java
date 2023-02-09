package i0.sealights.demo.calculator.service;

public class ExpressionValidator {
    void validate(final String expression) {
        if (expression == null || expression.isEmpty()) {
            throw new EvaluationException("blank expression");
        }
    }
}
