package engine.model;

import java.util.List;

public record QuizAnswer(List<Integer> answer) { // <- should be 'answer' not 'answers
}
