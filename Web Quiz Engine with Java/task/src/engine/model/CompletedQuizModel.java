package engine.model;

import java.time.LocalDateTime;

public record CompletedQuizModel(Long id, LocalDateTime completedAt) {
}
