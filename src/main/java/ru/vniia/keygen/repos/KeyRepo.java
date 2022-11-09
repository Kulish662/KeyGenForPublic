package ru.vniia.keygen.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vniia.keygen.domain.Key;

public interface KeyRepo extends JpaRepository<Key, Long> {

}
