package engine.model;

import java.util.List;

public record QuizModel(String title, String text, List<String> options) {
}
