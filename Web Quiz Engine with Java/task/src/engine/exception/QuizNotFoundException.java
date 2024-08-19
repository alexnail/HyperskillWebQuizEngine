package engine.exception;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(Integer id) {
        super("Quiz with id=%d not found.".formatted(id));
    }
}
