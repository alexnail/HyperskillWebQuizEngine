package engine.model;

import java.util.List;

public record QuizOutputModel(Integer id, String title, String text, List<String> options) {
}
