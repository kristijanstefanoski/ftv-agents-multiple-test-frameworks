package i0.sealights.demo.summator.api;

import static org.springframework.http.HttpStatus.CONFLICT;

import i0.sealights.demo.summator.service.SummatorService;
import i0.sealights.demo.summator.service.ValidationException;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummatorController {

    private final SummatorService summatorService;

    public SummatorController(SummatorService summatorService) {
        this.summatorService = summatorService;
    }

    @GetMapping("/sum/geometric")
    public Result evaluateExpression(
        final @RequestParam(name = "first", required = false) String first,
        final @RequestParam(name = "ratio", required = false) String ratio,
        final @RequestParam(name = "count", required = false) String count
    ) {
        validateArguments(first, ratio, count);
        final double result = summatorService.sum(
            summatorService.stringToBigDecimal(first),
            summatorService.stringToBigDecimal(ratio),
            summatorService.stringToInt(count)
        );
        return new Result(result);
    }

    private void validateArguments(String first, String ratio, String count) {
        if (isBlank(first)) {
            throw new ValidationException("Missing required argument: 'first'");
        }
        if (isBlank(ratio)) {
            throw new ValidationException("Missing required argument: 'ratio'");
        }
        if (isBlank(count)) {
            throw new ValidationException("Missing required argument: 'count'");
        }
    }

    private boolean isBlank(final String str) {
        return Objects.isNull(str) || str.isEmpty();
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResult> handleOtherExceptions(Exception exception) {
        final ErrorResult errorResult = new ErrorResult(exception.getMessage());
        return ResponseEntity.status(CONFLICT).body(errorResult);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorResult> handleOtherExceptions(ValidationException exception) {
        final ErrorResult errorResult = new ErrorResult(exception.getMessage());
        return ResponseEntity.badRequest().body(errorResult);
    }
}
