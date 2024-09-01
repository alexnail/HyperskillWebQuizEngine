package engine.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_completions")
public class QuizCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long quizId;

    private String userName;

    private LocalDateTime completedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String completedBy) {
        this.userName = completedBy;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
