package ru.KostarevaAnastasia.NauJava.service;

import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUsersByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
