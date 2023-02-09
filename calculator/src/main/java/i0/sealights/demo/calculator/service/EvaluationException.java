package i0.sealights.demo.calculator.service;

public class EvaluationException extends RuntimeException {

    public EvaluationException(String expression) {
        super("Invalid math exception: ' " + expression + " '");
    }
}
