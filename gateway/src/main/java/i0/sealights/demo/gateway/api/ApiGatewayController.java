package i0.sealights.demo.gateway.api;

import i0.sealights.demo.gateway.service.ApiUrlNotFound;
import i0.sealights.demo.gateway.service.BackendProxy;
import i0.sealights.demo.gateway.service.HostResolver;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGatewayController {

    HostResolver hostResolver;
    BackendProxy backendProxy;

    public ApiGatewayController(HostResolver hostResolver, BackendProxy backendProxy) {
        this.backendProxy = backendProxy;
        this.hostResolver = hostResolver;
    }

    @GetMapping("/api/{*uri}")
    public ResponseEntity<String> route(HttpServletRequest request) {

        // todo: need for fast debugging - can be removed after demo
        request.getHeaderNames().asIterator().forEachRemaining(element -> {
            System.out.println("HEADER: " + element + " > " + request.getHeader(element));
        });

        final String serviceUrl = hostResolver.resolveServiceUrl(request.getRequestURI());
        final Map<String, String[]> parameterMap = request.getParameterMap();

        return backendProxy.callUrl(serviceUrl, parameterMap);

    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResult> handleOtherExceptions(Exception exception) {
        final ErrorResult errorResult = new ErrorResult("Unexpected API Gateway exception: " + exception.getMessage());
        return ResponseEntity.internalServerError().body(errorResult);
    }

    @ExceptionHandler({ApiUrlNotFound.class})
    public ResponseEntity<ErrorResult> handleNotFoud(ApiUrlNotFound exception) {
        return ResponseEntity.notFound().header("X-Reason", exception.getMessage()).build();
    }

}
