package engine.controller;

import engine.exception.QuizNotFoundException;
import engine.model.QuizAnswer;
import engine.model.QuizFeedbackModel;
import engine.model.QuizInputModel;
import engine.model.QuizOutputModel;
import engine.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizzesController {

    private final QuizService quizService;

    public QuizzesController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public QuizOutputModel createQuiz(@Valid @RequestBody QuizInputModel input) {
        return quizService.createQuiz(input);
    }

    @GetMapping("/{id}")
    public QuizOutputModel getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }

    @GetMapping
    public List<QuizOutputModel> getAllQuizzes() {
        return quizService.getAll();
    }

    @PostMapping("/{id}/solve")
    public QuizFeedbackModel solveQuiz(@PathVariable Long id, @RequestBody QuizAnswer quizAnswer) {
        return quizService.solve(id, quizAnswer.answer());
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity handleQuizNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
