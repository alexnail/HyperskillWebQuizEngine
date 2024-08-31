package engine.exception;

public class NonUniqueUserException extends RuntimeException {
    public NonUniqueUserException(String email, Throwable e) {
        super(email + " is not unique.", e);
    }
}
