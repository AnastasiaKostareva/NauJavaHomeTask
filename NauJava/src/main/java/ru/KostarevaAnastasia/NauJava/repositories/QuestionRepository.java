package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.KostarevaAnastasia.NauJava.models.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}

