package i0.sealights.demo.calculator.api;

import i0.sealights.demo.calculator.service.CalculatorService;
import i0.sealights.demo.calculator.service.EvaluationException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/evaluate/{expression}")
    public Result evaluateExpression(final @PathVariable("expression") String expression,
        HttpServletRequest servletRequest) {

        // todo: need for fast debugging - can be removed after demo
        servletRequest.getHeaderNames().asIterator().forEachRemaining(element -> {
            System.out.println("HEADER: " + element + " > " + servletRequest.getHeader(element));
        });

        final double result = calculatorService.eval(expression);
        return new Result(result);
    }

    @ExceptionHandler({EvaluationException.class})
    public ResponseEntity<ErrorResult> handleEvaluationExceptions(EvaluationException exception) {
        final ErrorResult errorResult = new ErrorResult(exception.getMessage());
        return ResponseEntity.badRequest().body(errorResult);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResult> handleOtherExceptions(Exception exception) {
        final ErrorResult errorResult = new ErrorResult("Nobody expects the Spanish Inquisition: " + exception.getMessage());
        return ResponseEntity.badRequest().body(errorResult);
    }
}
