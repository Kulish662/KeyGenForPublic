package ru.vniia.keygen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vniia.keygen.domain.Role;
import ru.vniia.keygen.domain.User;
import ru.vniia.keygen.domain.UserEditLog;
import ru.vniia.keygen.repos.UserEditLogRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Logger {
    @Autowired
    private UserEditLogRepo userEditLogRepo;

    public void logUserEdit(Set<Role> oldRoles, User userToEdit, User author) {
        UserEditLog logUnit = new UserEditLog();
        logUnit.setAuthor(author);
        logUnit.setTarget(userToEdit);
        logUnit.setDate(new Date(System.currentTimeMillis()));
        logUnit.setOldRoles(getStringFromRolesSet(oldRoles));
        logUnit.setNewRoles(getStringFromRolesSet(userToEdit.getRoles()));
        userEditLogRepo.save(logUnit);
    }

    private String getStringFromRolesSet(Set<Role> set){
        return set.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
}
