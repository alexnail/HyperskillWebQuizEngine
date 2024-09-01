package engine.controller;

import engine.exception.QuizCompletionsUnauthorizedException;
import engine.exception.QuizDeleteForbiddenException;
import engine.exception.QuizNotFoundException;
import engine.model.*;
import engine.service.QuizService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/completed")
    public Page<CompletedQuizModel> getCompletedQuizzes(@RequestParam("page") Integer page,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new QuizCompletionsUnauthorizedException();
        }
        return quizService.getCompletedQuizzes(userDetails.getUsername(), page);
    }

    @PostMapping("/{id}/solve")
    public QuizFeedbackModel solveQuiz(@PathVariable Long id, @RequestBody QuizAnswer quizAnswer,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return quizService.solve(id, quizAnswer.answer(), userDetails.getUsername());
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

    @ExceptionHandler(QuizDeleteForbiddenException.class)
    public ResponseEntity handleQuizDeleteForbidden() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(QuizCompletionsUnauthorizedException.class)
    public ResponseEntity handleQuizCompletionsUnauthorized() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
