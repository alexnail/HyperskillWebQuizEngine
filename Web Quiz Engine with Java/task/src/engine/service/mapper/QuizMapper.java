package engine.service.mapper;

import engine.model.Quiz;
import engine.model.QuizInputModel;
import engine.model.QuizOutputModel;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {
    public Quiz inputToEntity(QuizInputModel input, String userName) {
        var quiz = new Quiz();
        quiz.setTitle(input.title());
        quiz.setText(input.text());
        quiz.setOptions(input.options());
        quiz.setAnswers(input.answer());
        quiz.setCreatedBy(userName);
        return quiz;
    }

    public QuizOutputModel toOutputModel(Quiz quiz) {
        return new QuizOutputModel(quiz.getId(), quiz.getTitle(), quiz.getText(), quiz.getOptions());
    }
}
