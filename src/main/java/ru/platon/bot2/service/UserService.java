package ru.platon.bot2.service;

import org.springframework.stereotype.Service;
import ru.platon.bot2.entities.User;
import ru.platon.bot2.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод проверяет прошел ли регистрацию пользователь
     */
    public boolean userRegistered(Long user_id){
        return userRepository.findById(user_id).isPresent();
    }

    public User getUesrIsBD(Long user_id){
        Optional<User> optional = userRepository.findById(user_id);
        return optional.orElseGet(User::new);
    }
}
