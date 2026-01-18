package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.dto.OptionCreateDto;
import ru.KostarevaAnastasia.NauJava.models.Option;

public interface OptionService {

    /**
     * Создаёт новый вариант ответа, привязанный к вопросу.
     * @param dto данные для создания варианта
     * @return сохранённый вариант ответа
     */
    Option createOption(OptionCreateDto dto);
}