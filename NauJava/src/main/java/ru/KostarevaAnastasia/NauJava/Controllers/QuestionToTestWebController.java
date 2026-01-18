package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.KostarevaAnastasia.NauJava.dto.QuestionFormDto;
import ru.KostarevaAnastasia.NauJava.models.*;
import ru.KostarevaAnastasia.NauJava.service.QuestionService;
import ru.KostarevaAnastasia.NauJava.service.QuestionToTestService;
import ru.KostarevaAnastasia.NauJava.service.TestService;
import ru.KostarevaAnastasia.NauJava.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tests/{testId}/questions")
public class QuestionToTestWebController {
    @Autowired
    private TestService testService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionToTestService questionToTestService;

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String showAddQuestionForm(@PathVariable Long testId, Model model) {
        Test test = testService.findById(testId);
        if (test == null) {
            return "redirect:/tests";
        }
        model.addAttribute("test", test);
        model.addAttribute("questionToTest", new QuestionToTest());
        model.addAttribute("allQuestions", questionService.findAll());
        return "questionToTestForm";
    }

    @PostMapping("/{questionId}/delete")
    public String removeQuestionFromTest(
            @PathVariable Long testId,
            @PathVariable Long questionId,
            RedirectAttributes redirectAttrs) {

        questionToTestService.deleteByTestIdAndQuestionId(testId, questionId);
        redirectAttrs.addFlashAttribute("message", "Вопрос удалён из теста");
        return "redirect:/tests/" + testId + "/edit";
    }

    @PostMapping
    public String addQuestionToTest(@PathVariable Long testId,
                                    @ModelAttribute QuestionToTest questionToTest,
                                    @RequestParam(defaultValue = "1") Integer numberPoints,
                                    @RequestParam(defaultValue = "1") Integer orderIndex) {
        Test test = testService.findById(testId);
        if (test == null) {
            return "redirect:/tests";
        }
        Question question = questionService.findById(questionToTest.getQuestion().getId());
        if (question == null) {
            return "redirect:/tests/" + testId + "/edit";
        }

        questionToTest.setTest(test);
        questionToTest.setQuestion(questionService.findById(questionToTest.getQuestion().getId()));
        questionToTest.setSortingOrder(orderIndex);
        questionToTest.setNumberPoints(numberPoints);

        questionToTestService.save(questionToTest);
        return "redirect:/tests/" + testId + "/edit";
    }

    @GetMapping("/create")
    public String showCreateQuestionForm(@PathVariable Long testId, Model model) {
        Test test = testService.findById(testId);
        if (test == null) return "redirect:/tests";

        long nextOrder = questionToTestService.countByTestId(testId) + 1;
        model.addAttribute("testId", testId);
        model.addAttribute("nextOrder", nextOrder);
        model.addAttribute("questionTypes", QuestionType.values());
        return "questions/createForTest";
    }

    @PostMapping("/create")
    public String createQuestionAndAddToTest(
            @PathVariable Long testId,
            @ModelAttribute QuestionFormDto dto,
            BindingResult bindingResult,
            @RequestParam String authorName,
            RedirectAttributes redirectAttrs,
            @RequestParam(defaultValue = "1") Integer points,
            @RequestParam(defaultValue = "1") Integer orderIndex) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(err ->
                    System.out.println("Ошибка: " + err.getDefaultMessage()));
            redirectAttrs.addFlashAttribute("error", "Ошибка валидации");
            return "redirect:/questions/new";
        }

        Question question = new Question();
        question.setTextQuestion(dto.textQuestion());
        question.setTheme(dto.theme());
        question.setQuestionType(dto.questionType());

        User author = userService.getOrCreateUser(authorName);
        question.setAuthor(author);

        List<Option> options = IntStream.range(0, dto.optionText().size())
                .mapToObj(i -> {
                    Option opt = new Option();
                    opt.setText(dto.optionText().get(i));
                    opt.setCorrect(dto.optionCorrect().size() > i ? dto.optionCorrect().get(i) : false);
                    return opt;
                })
                .collect(Collectors.toList());


        questionService.createQuestionWithOptions(question, options);

        Test test = testService.findById(testId);
        QuestionToTest qt = new QuestionToTest();
        qt.setTest(test);
        qt.setQuestion(question);
        qt.setNumberPoints(points);
        qt.setSortingOrder(orderIndex);

        questionToTestService.save(qt);
        redirectAttrs.addFlashAttribute("message", "Вопрос создан и добавлен в тест!");
        return "redirect:/tests/" + testId + "/edit";
    }
}
