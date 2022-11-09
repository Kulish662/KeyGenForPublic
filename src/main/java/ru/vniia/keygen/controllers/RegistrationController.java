package ru.vniia.keygen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vniia.keygen.domain.Role;
import ru.vniia.keygen.domain.User;
import ru.vniia.keygen.repos.UserRepo;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){
        if(fieldsEmpty(user)) return "registration";
        if(userExists(user)) {
            model.addAttribute("message", "Данный пользователь уже существует!");
            return "registration";
        }
        configurateNewUser(user);
        userRepo.save(user);
        return "redirect:/login";
    }

    private boolean fieldsEmpty(User user) {
        return user.getUsername().isEmpty() || user.getPassword().isEmpty();
    }

    private boolean userExists(User user){
        return userRepo.findByUsername(user.getUsername()) != null;
    }

    private void configurateNewUser(User user){
        user.setActive(true);
    }
}
