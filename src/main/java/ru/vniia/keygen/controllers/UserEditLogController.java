package ru.vniia.keygen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vniia.keygen.repos.UserEditLogRepo;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserEditLogController {
    @Autowired
    private UserEditLogRepo userEditLogRepo;

    @GetMapping("/usereditlog")
    public String main(Model model) {
        model.addAttribute("logs", userEditLogRepo.findAll());
        return "userEditLog";
    }
}
