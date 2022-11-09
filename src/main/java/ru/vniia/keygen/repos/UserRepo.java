package ru.vniia.keygen.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vniia.keygen.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
