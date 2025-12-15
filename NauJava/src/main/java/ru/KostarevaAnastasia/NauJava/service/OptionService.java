package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.dto.OptionCreateDto;
import ru.KostarevaAnastasia.NauJava.models.Option;

public interface OptionService {

    /**
     * Создаёт новый вариант ответа, привязанный к вопросу.
     * @param dto данные для создания варианта
     * @param currentUsername имя текущего авторизованного пользователя
     * @return сохранённый вариант ответа
     * @throws org.springframework.security.access.AccessDeniedException если пользователь не автор вопроса и не ADMIN
     * @throws jakarta.persistence.EntityNotFoundException если вопрос не найден
     */
    Option createOption(OptionCreateDto dto, String currentUsername);
}