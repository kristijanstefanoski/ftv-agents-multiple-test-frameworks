package i0.sealights.demo.gateway.service;

public class BackendExecutionException extends RuntimeException {

    public BackendExecutionException(String message, Throwable reason) {
        super(message, reason);
    }
}
