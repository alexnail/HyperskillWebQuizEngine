package engine.model;

import java.util.List;

public record QuizOutputModel(Long id, String title, String text, List<String> options) {
}
