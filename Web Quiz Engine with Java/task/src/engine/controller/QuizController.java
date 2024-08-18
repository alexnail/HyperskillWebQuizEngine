package engine.controller;

import engine.model.QuizFeedbackModel;
import engine.model.QuizModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @GetMapping()
    public QuizModel getQuiz() {
        return new QuizModel(
                "The Java Logo",
                "What is depicted on the Java logo?",
                List.of("Robot","Tea leaf","Cup of coffee","Bug")
        );
    }

    @PostMapping()
    public QuizFeedbackModel answerQuiz(@RequestParam Integer answer) {
        return answer == 2
                ? new QuizFeedbackModel(true, "Congratulations, you're right!")
                : new QuizFeedbackModel(false, "Wrong answer! Please, try again.");
    }
}
