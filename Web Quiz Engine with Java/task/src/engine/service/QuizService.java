package engine.service;

import engine.entity.QuizCompletion;
import engine.exception.QuizDeleteForbiddenException;
import engine.exception.QuizNotFoundException;
import engine.model.CompletedQuizModel;
import engine.model.QuizFeedbackModel;
import engine.model.QuizInputModel;
import engine.model.QuizOutputModel;
import engine.repository.CompletionRepository;
import engine.repository.QuizRepository;
import engine.service.mapper.CompletionMapper;
import engine.service.mapper.QuizMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository repository;
    private final CompletionRepository completionRepository;
    private final QuizMapper mapper;
    private final CompletionMapper completionMapper;

    public QuizService(QuizRepository repository, QuizMapper mapper,
                       CompletionRepository completionRepository, CompletionMapper completionMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.completionRepository = completionRepository;
        this.completionMapper = completionMapper;
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

    public Page<QuizOutputModel> getAll(Integer page) {
        var quizPage = repository.findAll(PageRequest.of(page, 10));
        return quizPage.map(mapper::toOutputModel);
    }

    public QuizFeedbackModel solve(Long id, List<Integer> answers, String userName) {
        var quiz = repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));

        var expectedAnswers = List.copyOf(quiz.getAnswers());
        if (CollectionUtils.isEmpty(answers) && CollectionUtils.isEmpty(expectedAnswers)) {
            return getFeedbackModel(true, id, userName);
        } else if (CollectionUtils.isEmpty(answers) || CollectionUtils.isEmpty(expectedAnswers)) {
            return getFeedbackModel(false, id, userName);
        } else {
            return getFeedbackModel(expectedAnswers.equals(answers), id, userName) ;
        }
    }

    private QuizFeedbackModel getFeedbackModel(boolean success, Long id, String userName) {
        if (success) {
            completionRepository.save(completionMapper.toEntity(id, userName));
            return mapper.success();
        }
        return mapper.failure();
    }

    public QuizOutputModel deleteQuiz(Long id) throws QuizDeleteForbiddenException {
        var quiz = repository.findById(id).orElse(null);
        if (quiz == null) {
            return null;
        }
        if (quiz.getCreatedBy().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            repository.delete(quiz);
            return mapper.toOutputModel(quiz);
        } else {
            throw new QuizDeleteForbiddenException();
        }
    }

    public Page<CompletedQuizModel> getCompletedQuizzes(String userName, Integer page) {
        Page<QuizCompletion> quizCompletions = completionRepository.findAllByUserName(
                userName, PageRequest.of(page, 10, Sort.by("completedAt").descending()));
        return quizCompletions.map(completionMapper::toModel);
    }
}
