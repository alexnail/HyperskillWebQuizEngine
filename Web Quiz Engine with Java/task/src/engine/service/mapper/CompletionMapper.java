package engine.service.mapper;

import engine.entity.QuizCompletion;
import engine.model.CompletedQuizModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CompletionMapper {

    public QuizCompletion toEntity(Long quizId, String userName) {
        var completion = new QuizCompletion();
        completion.setQuizId(quizId);
        completion.setUserName(userName);
        completion.setCompletedAt(LocalDateTime.now());
        return completion;
    }

    public CompletedQuizModel toModel(QuizCompletion quizCompletion) {
        return new CompletedQuizModel(quizCompletion.getQuizId(), quizCompletion.getCompletedAt());
    }
}
