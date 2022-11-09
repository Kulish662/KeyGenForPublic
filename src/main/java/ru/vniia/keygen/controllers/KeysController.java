package ru.vniia.keygen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vniia.keygen.domain.Key;
import ru.vniia.keygen.repos.KeyRepo;

import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class KeysController {
    @Autowired
    private KeyRepo keyRepo;

    @GetMapping("/keys")
    public String main(Model model){
        List<Key> keys = keyRepo.findAll();
        model.addAttribute("keys", keys);
        return "keys";
    }
}
