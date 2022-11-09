package ru.vniia.keygen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vniia.keygen.domain.Role;
import ru.vniia.keygen.domain.User;
import ru.vniia.keygen.repos.UserRepo;
import ru.vniia.keygen.services.Logger;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')") // проверяет перед выполнением метода наличие у пользователя роли АДМИН
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private Logger logger;

    @GetMapping()
    public String main(Model model) {
        List<User> allUsers = userRepo.findAll();
        model.addAttribute("users", allUsers);
        return "userList";
    }

    @GetMapping("/{id}")
    public String onEnterUserEditPage(@PathVariable("id") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    /**
     * Сохранение ролей пользователя
     *
     * @param rolesToSet
     * @param userToEdit
     * @return
     */
    @PostMapping("/edituser")
    public String editUser(@RequestParam Set<Role> rolesToSet, @RequestParam("userToEditId") User userToEdit) {
        Set<Role> oldRoles = userToEdit.getRoles();
        if (oldRoles.containsAll(rolesToSet)) return "redirect:"; //ничего не поменяли, ничего не делаем
        //todo Записать в лог(БД) кто кому какие роли поставил
        userToEdit.getRoles().clear();
        userToEdit.setRoles(rolesToSet);
        userRepo.save(userToEdit);

        logEditUser(userToEdit, oldRoles);
        return "redirect:";
    }

    private void logEditUser(User userToEdit, Set<Role> oldRoles) {
        try {
            User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            logger.logUserEdit(oldRoles, userToEdit, author);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
