package ru.KostarevaAnastasia.NauJava.Controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.KostarevaAnastasia.NauJava.dto.TestSubmissionRequest;
import ru.KostarevaAnastasia.NauJava.dto.UserAnswerDto;
import ru.KostarevaAnastasia.NauJava.models.*;
import ru.KostarevaAnastasia.NauJava.repositories.OptionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionToTestRepository;
import ru.KostarevaAnastasia.NauJava.repositories.UserAnswerRepository;
import ru.KostarevaAnastasia.NauJava.service.ScoreService;
import ru.KostarevaAnastasia.NauJava.service.UserService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/tests")
public class TestSubmissionController {

    @Autowired
    private UserAnswerRepository userAnswerRepository;
    @Autowired
    private QuestionToTestRepository questionToTestRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserService userService;

    @PostMapping("/{testId}/submit")
    @Transactional
    public ResponseEntity<Map<String, Object>> submitTest(
            @PathVariable Long testId,
            @RequestBody TestSubmissionRequest request) {

        String participantName = request.participantName();
        if (participantName == null || participantName.trim().isEmpty()) {
            participantName = "anonymous";
        }
        User user = userService.getOrCreateUser(participantName);

        List<QuestionToTest> qtList = questionToTestRepository.findByTestIdOrderBySortingOrderAsc(testId);
        Map<Long, QuestionToTest> questionMap = qtList.stream()
                .collect(Collectors.toMap(qt -> qt.getQuestion().getId(), qt -> qt));

        List<Long> allOptionIds = request.answers().stream() // ← используем request.answers()
                .flatMap(a -> a.selectedOptionIds().stream())
                .distinct()
                .collect(Collectors.toList());

        Iterable<Option> iterable = optionRepository.findAllById(allOptionIds);
        List<Option> allOptions = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        Map<Long, Option> optionMap = allOptions.stream()
                .collect(Collectors.toMap(Option::getId, opt -> opt));

        int totalScore = 0;
        int maxScore = qtList.stream().mapToInt(QuestionToTest::getNumberPoints).sum();

        List<UserAnswer> userAnswers = new ArrayList<>();

        for (UserAnswerDto dto : request.answers()) { // ← здесь тоже
            QuestionToTest qt = questionMap.get(dto.questionId());
            if (qt == null) continue;

            Question question = qt.getQuestion();
            Test test = qt.getTest();

            List<Option> correctOptions = optionRepository.findByQuestionId(question.getId())
                    .stream()
                    .filter(Option::isCorrect)
                    .collect(Collectors.toList());

            Set<Long> correctIds = correctOptions.stream().map(Option::getId).collect(Collectors.toSet());
            Set<Long> submittedIds = new HashSet<>(dto.selectedOptionIds());

            boolean isCorrect = false;
            if (question.getQuestionType() == QuestionType.SINGLE) {
                isCorrect = submittedIds.size() == 1 && correctIds.equals(submittedIds);
            } else if (question.getQuestionType() == QuestionType.MULTIPLE) {
                isCorrect = correctIds.equals(submittedIds);
            }

            if (isCorrect) {
                totalScore += qt.getNumberPoints();
            }

            for (Long optionId : dto.selectedOptionIds()) {
                Option option = optionMap.get(optionId);
                if (option == null) continue;

                UserAnswerId id = new UserAnswerId(
                        user.getId(),
                        optionId,
                        test.getId(),
                        question.getId()
                );

                UserAnswer ua = new UserAnswer();
                ua.setId(id);
                ua.setUser(user);
                ua.setOption(option);
                ua.setTest(test);
                ua.setQuestion(question);

                userAnswers.add(ua);
            }
        }

        try {
            userAnswerRepository.saveAll(userAnswers);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка сохранения ответов", e);
        }

        scoreService.saveScore(testId, participantName, totalScore);

        Map<String, Object> result = new HashMap<>();
        result.put("score", totalScore);
        result.put("maxScore", maxScore);
        result.put("percentage", Math.round(100.0 * totalScore / maxScore));

        return ResponseEntity.ok(result);
    }
}
