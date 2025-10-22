package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.KostarevaAnastasia.NauJava.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
}