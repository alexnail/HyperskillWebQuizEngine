package engine.model;

import java.util.List;

public record QuizInputModel(String title, String text, List<String> options, Integer answer) {
}
