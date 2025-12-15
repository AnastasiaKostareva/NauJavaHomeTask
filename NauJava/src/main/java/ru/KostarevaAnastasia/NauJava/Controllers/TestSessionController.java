package ru.KostarevaAnastasia.NauJava.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.KostarevaAnastasia.NauJava.dto.TestSessionDto;
import ru.KostarevaAnastasia.NauJava.service.TestSessionService;

@RestController
@RequestMapping("/api/tests")
@Tag(name = "Сессии тестов", description = "Начало прохождения теста")
public class TestSessionController {

    private final TestSessionService testSessionService;

    public TestSessionController(TestSessionService testSessionService) {
        this.testSessionService = testSessionService;
    }

    @GetMapping("/{testId}/start")
    public TestSessionDto startTest(@PathVariable Long testId) {
        return testSessionService.startTest(testId);
    }
}
