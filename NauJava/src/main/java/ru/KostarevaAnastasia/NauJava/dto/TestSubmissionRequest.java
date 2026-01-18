package ru.KostarevaAnastasia.NauJava.dto;

import java.util.List;

public record TestSubmissionRequest(
        String participantName,
        List<UserAnswerDto> answers
) {}