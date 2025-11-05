package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUsersByUsername(String name);
    User addUser(User user);
}
