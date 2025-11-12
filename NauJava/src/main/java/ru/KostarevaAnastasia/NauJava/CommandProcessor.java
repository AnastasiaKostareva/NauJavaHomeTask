package ru.KostarevaAnastasia.NauJava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.KostarevaAnastasia.NauJava.businessLogic.QuestionServiceOld;
import ru.KostarevaAnastasia.NauJava.models.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CommandProcessor
{
    private final QuestionServiceOld questionService;
    @Autowired
    public CommandProcessor(QuestionServiceOld questionService)
    {
        this.questionService = questionService;
    }
    public void processCommand(String input)
    {
        String[] cmd = parseArgs(input);
        switch (cmd[0])
        {
            case "create" ->
            {
                String[] optionsArray = Arrays.copyOfRange(cmd, 4, cmd.length);
                List<String> options = Arrays.asList(optionsArray);

                questionService.createQuestion(Long.valueOf(cmd[1]), cmd[2], cmd[3], options);
                System.out.println("Вопрос успешно добавлен...");
            }
            case "find" ->
            {
                Question question = questionService.findById(Long.valueOf(cmd[1]));
                if (question != null) {
                    System.out.println("Вопрос успешно найден..." + question.toString());
                }
                else {
                    System.out.println("Такого вопроса нет...");
                }
            }
            case "delete" ->
            {
                questionService.deleteById(Long.valueOf(cmd[1]));
                System.out.println("Вопрос успешно удален...");
            }
            case "updText" ->
            {
                questionService.updateText(Long.valueOf(cmd[1]), cmd[2]);
                System.out.println("Текст вопроса успешно обновлен...");
            }
            case "updTheme" ->
            {
                questionService.updateTheme(Long.valueOf(cmd[1]), cmd[2]);
                System.out.println("Тема вопроса успешно обновлена...");
            }
            default -> System.out.println("Введена неизвестная команда...");
        }
    }

    private String[] parseArgs(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : input.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (!current.isEmpty()) {
                    tokens.add(current.toString());
                    current.setLength(0);
                }
            } else {
                current.append(c);
            }
        }
        if (!current.isEmpty()) {
            tokens.add(current.toString());
        }
        return tokens.toArray(new String[0]);
    }
}
