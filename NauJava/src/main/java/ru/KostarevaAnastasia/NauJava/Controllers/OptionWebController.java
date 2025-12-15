package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.KostarevaAnastasia.NauJava.dto.OptionCreateDto;
import ru.KostarevaAnastasia.NauJava.service.OptionService;

@Controller
@PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')")
public class OptionWebController {
    @Autowired
    private OptionService optionService;

    @GetMapping("/questions/{questionId}/options/new")
    public String showCreateOptionForm(
            @PathVariable Long questionId,
            org.springframework.ui.Model model) {
        model.addAttribute("questionId", questionId);
        return "options/create";
    }

    @PostMapping("/options")
    public String handleCreateOption(
            @RequestParam String text,
            @RequestParam(required = false, defaultValue = "false") boolean correct,
            @RequestParam Long questionId,
            org.springframework.security.core.Authentication authentication,
            RedirectAttributes redirectAttributes) {

        OptionCreateDto dto = new OptionCreateDto(text, correct, questionId);

        optionService.createOption(dto, authentication.getName());

        redirectAttributes.addFlashAttribute("message", "Вариант успешно добавлен!");
        return "redirect:/questions/" + questionId;
    }
}
