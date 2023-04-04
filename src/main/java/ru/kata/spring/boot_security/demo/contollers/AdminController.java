package ru.kata.spring.boot_security.demo.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String showAdminPage(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("newUser", new User());
        return "admin-profile";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
}

