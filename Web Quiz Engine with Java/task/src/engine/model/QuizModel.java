package engine.model;

import java.util.List;

public record QuizModel(Integer id, String title, String text, List<String> options, List<Integer> answer) {
    public QuizOutputModel toOutput() {
        return new QuizOutputModel(id, title, text, options);
    }
}
