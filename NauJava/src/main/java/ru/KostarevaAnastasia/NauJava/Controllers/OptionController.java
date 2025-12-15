package ru.KostarevaAnastasia.NauJava.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.KostarevaAnastasia.NauJava.dto.OptionCreateDto;
import ru.KostarevaAnastasia.NauJava.models.Option;
import ru.KostarevaAnastasia.NauJava.service.OptionService;

@RestController
@RequestMapping("/api/options")
@PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<Option> createOption(
            @Valid @RequestBody OptionCreateDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Option option = optionService.createOption(dto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(option);
    }
}
