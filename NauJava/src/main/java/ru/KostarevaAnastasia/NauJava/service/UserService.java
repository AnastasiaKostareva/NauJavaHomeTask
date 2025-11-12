package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.models.User;

import java.util.Optional;

/**
 * Сервис для управления пользователями
 */
public interface UserService {
    /**
     * Находит пользователя по уникальному имени
     * @param name имя пользователя
     * @return Optional, содержащий найденного пользователя
     * или пустой Optional, если пользователь с таким именем не существует
     */
    Optional<User> getUsersByUsername(String name);

    /**
     * Регистрирует нового пользователя в системе
     * @param user объект пользователя для сохранения
     * @return сохранённый экземпляр пользователя
     */
    User addUser(User user);
}
