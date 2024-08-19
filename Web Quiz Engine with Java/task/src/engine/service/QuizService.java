package engine.service;

import engine.exception.QuizNotFoundException;
import engine.model.QuizFeedbackModel;
import engine.model.QuizInputModel;
import engine.model.QuizModel;
import engine.model.QuizOutputModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final List<QuizModel> quizzes = new ArrayList<>();

    public QuizOutputModel createQuiz(QuizInputModel input) {
        QuizModel quizModel = new QuizModel(quizzes.size() + 1, input.title(), input.text(), input.options(), input.answer());
        quizzes.add(quizModel);
        return quizModel.toOutput();
    }

    public QuizOutputModel getQuiz(Integer id) {
        if (id > 0 && id <= quizzes.size()) {
            return quizzes.get(id - 1).toOutput();
        }
        throw new QuizNotFoundException(id);
    }

    public List<QuizOutputModel> getAll() {
        return quizzes.stream().map(QuizModel::toOutput).toList();
    }

    public QuizFeedbackModel solve(Integer id, Integer answer) {
        if (id > 0 && id <= quizzes.size()) {
            QuizModel quiz = quizzes.get(id - 1);
            return quiz.answer() == answer
                    ? new QuizFeedbackModel(true, "Congratulations, you're right!")
                    : new QuizFeedbackModel(false, "Wrong answer! Please, try again.");
        }
        throw new QuizNotFoundException(id);
    }
}
