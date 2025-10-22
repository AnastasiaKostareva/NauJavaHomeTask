package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.KostarevaAnastasia.NauJava.models.Option;

import java.util.List;

public interface OptionRepository extends CrudRepository<Option, Long> {
    List<Option> findByQuestionID(Long questionId);
}