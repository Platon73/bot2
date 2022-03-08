package ru.platon.bot2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platon.bot2.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
