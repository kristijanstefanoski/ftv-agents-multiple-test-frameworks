package i0.sealights.demo.calculator.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CalculatorServiceTest {

    static CalculatorService calculatorService;

    @BeforeAll
    public static void setup() {
        ExpressionValidator expressionValidator = new ExpressionValidator();
        calculatorService = new CalculatorService(expressionValidator);
    }

    @ParameterizedTest
    @MethodSource("testArguments")
    public void shouldEvaluate(String expression, double expectedResult) {
        // when
        double result = calculatorService.eval(expression);

        //then
        assertEquals(expectedResult, result);

    }

    @ParameterizedTest
    @MethodSource("blankExpressions")
    public void shouldThrowExceptionOnInvalidExpression(String expression) {
        // when
        Exception exception = assertThrows(EvaluationException.class, () -> {
            calculatorService.eval(expression);
        });

        //then
        assertEquals(exception.getMessage(), "Invalid math exception: ' blank expression '");
    }

    @Test
    public void shouldThrowExceptionOnNullExpression() {
        // when
        Exception exception = assertThrows(EvaluationException.class, () -> {
            calculatorService.eval(null);
        });

        //then
        assertEquals(exception.getMessage(), "Invalid math exception: ' blank expression '");
    }

    public static Stream<Arguments> blankExpressions() {
        return Stream.of(
            Arguments.of("")
        );
    }

    private static Stream<Arguments> testArguments() {
        return Stream.of(
            Arguments.of("2 * 2", 4.0),
            Arguments.of("2 * 4", 8.0),
            Arguments.of("13 - 9", 4.0),
            Arguments.of("15 - 2", 13.0),
            Arguments.of("3 * 3 + 4", 13.0),
            Arguments.of("(3 + 3) * 3", 18.0),
            Arguments.of("13 - 9", 4.0),
            Arguments.of("13 - 9", 4.0),
            Arguments.of("cos(0.0)", 1.0),
            Arguments.of("100 / 20", 5.0)
        );
    }
}