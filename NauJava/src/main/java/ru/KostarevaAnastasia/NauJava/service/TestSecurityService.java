package ru.KostarevaAnastasia.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.models.Test;

@Service("testSecurityService")
public class TestSecurityService {

    @Autowired
    private TestService testService;

    public boolean canEditTest(Authentication authentication, Long testId) {
        String username = authentication.getName();
        Test test = testService.findById(testId);
        if (test == null) return false;

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        return test.getCreator() != null &&
                username.equals(test.getCreator().getUsername());
    }
}
