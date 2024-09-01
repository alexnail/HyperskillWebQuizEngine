package engine.controller;

import engine.exception.QuizDeleteNotAuthorizedException;
import engine.exception.QuizNotFoundException;
import engine.model.QuizAnswer;
import engine.model.QuizFeedbackModel;
import engine.model.QuizInputModel;
import engine.model.QuizOutputModel;
import engine.service.QuizService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public Page<QuizOutputModel> getAllQuizzes(@RequestParam("page") Integer page) {
        return quizService.getAll(page);
    }

    @PostMapping("/{id}/solve")
    public QuizFeedbackModel solveQuiz(@PathVariable Long id, @RequestBody QuizAnswer quizAnswer) {
        return quizService.solve(id, quizAnswer.answer());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuiz(@PathVariable Long id) {
        QuizOutputModel quiz = quizService.deleteQuiz(id);
        if (quiz != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity handleQuizNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuizDeleteNotAuthorizedException.class)
    public ResponseEntity handleQuizDeleteNotAuthorized() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
