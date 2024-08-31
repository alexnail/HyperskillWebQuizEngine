package engine.controller;

import engine.model.QuizInputModel;
import engine.model.QuizOutputModel;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("for local runs")
public class QuizzesControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterClass
    public static void afterClass() throws Exception {
        //System.out.println("After test class cleanup");
    }

    @Test
    public void createQuiz() {
        var inputModel = new QuizInputModel(
                "Quiz Input", "lorem ipsum",
                List.of("Option 1", "Option 2", "Option 3"), List.of(1, 3));

        var quizModel = restTemplate.postForObject(
                "http://localhost:" + port + "/api/quizzes", inputModel, QuizOutputModel.class);

        assertThat(quizModel).isNotNull().isInstanceOf(QuizOutputModel.class);
        assertThat(quizModel.id()).isNotNull().isGreaterThanOrEqualTo(1L);
        assertThat(quizModel.title()).isEqualTo(inputModel.title());
        assertThat(quizModel.text()).isEqualTo(inputModel.text());

        inputModel = new QuizInputModel(
                "Quiz Input 2", "input 2 description",
                List.of("Option 1", "Option 2", "Option 3"), List.of(2));

        quizModel = restTemplate.postForObject(
                "http://localhost:" + port + "/api/quizzes", inputModel, QuizOutputModel.class);

        assertThat(quizModel).isNotNull().isInstanceOf(QuizOutputModel.class);
        assertThat(quizModel.id()).isNotNull().isGreaterThanOrEqualTo(2L);
        assertThat(quizModel.title()).isEqualTo(inputModel.title());
        assertThat(quizModel.text()).isEqualTo(inputModel.text());
    }

    @Test
    public void getQuiz() {
        var quizModel = restTemplate.getForObject(
                "http://localhost:" + port + "/api/quizzes/1", QuizOutputModel.class);

        System.out.println(quizModel);
        assertThat(quizModel).isNotNull().isInstanceOf(QuizOutputModel.class);
        assertThat(quizModel.id()).isNotNull().isEqualTo(1);
    }

    @Test
    public void getAllQuizzes() {
        List<QuizOutputModel> quizzes = restTemplate.getForObject(
                "http://localhost:" + port + "/api/quizzes", List.class);

        assertThat(quizzes).isNotNull().isInstanceOf(List.class);
        assertThat(quizzes).isNotEmpty();
        assertThat(quizzes).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    public void solveQuiz() {
    }

    @Test
    public void handleQuizNotFound() {
    }
}