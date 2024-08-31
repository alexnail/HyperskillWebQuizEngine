package engine.service;

import engine.exception.QuizDeleteNotAuthorizedException;
import engine.exception.QuizNotFoundException;
import engine.model.QuizFeedbackModel;
import engine.model.QuizInputModel;
import engine.model.QuizOutputModel;
import engine.repository.QuizRepository;
import engine.service.mapper.QuizMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class QuizService {

    private final QuizRepository repository;
    private final QuizMapper mapper;

    public QuizService(QuizRepository repository, QuizMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public QuizOutputModel createQuiz(QuizInputModel input) {
        var quiz = repository.save(
                mapper.inputToEntity(input,
                SecurityContextHolder.getContext().getAuthentication().getName()));
        return mapper.toOutputModel(quiz);
    }

    public QuizOutputModel getQuiz(Long id) {
        var quiz = repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
        return mapper.toOutputModel(quiz);
    }

    public List<QuizOutputModel> getAll() {
        var quizzes = repository.findAll();
        return StreamSupport.stream(quizzes.spliterator(), false)
                .map(mapper::toOutputModel)
                .toList();
    }

    public QuizFeedbackModel solve(Long id, List<Integer> answers) {
        var quiz = repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));

        var expectedAnswers = List.copyOf(quiz.getAnswers());
        if (CollectionUtils.isEmpty(answers) && CollectionUtils.isEmpty(expectedAnswers)) {
            return new QuizFeedbackModel(true, "Congratulations, you're right!");
        } else if ((CollectionUtils.isEmpty(answers) && !CollectionUtils.isEmpty(expectedAnswers))
                || (!CollectionUtils.isEmpty(answers) && CollectionUtils.isEmpty(expectedAnswers))) {
            return new QuizFeedbackModel(false, "Wrong answer! Please, try again.");
        } else {
            return expectedAnswers.equals(answers)
                    ? new QuizFeedbackModel(true, "Congratulations, you're right!")
                    : new QuizFeedbackModel(false, "Wrong answer! Please, try again.");
        }
    }

    public QuizOutputModel deleteQuiz(Long id) throws QuizDeleteNotAuthorizedException {
        var quiz = repository.findById(id).orElse(null);
        if (quiz == null) {
            return null;
        }
        if (quiz.getCreatedBy().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            repository.delete(quiz);
            return mapper.toOutputModel(quiz);
        } else {
            throw new QuizDeleteNotAuthorizedException();
        }
    }
}
