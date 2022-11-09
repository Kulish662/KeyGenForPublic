package ru.vniia.keygen.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vniia.keygen.domain.Organization;

public interface OrganisationRepo extends JpaRepository<Organization, Long> {

    Organization findByName(String name);

}
