package ru.vniia.keygen.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vniia.keygen.domain.UserEditLog;

public interface UserEditLogRepo extends JpaRepository<UserEditLog, Long> {
}
