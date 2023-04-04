package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initializeDB() {
        roleService.save(new Role("ROLE_ADMIN"));
        roleService.save(new Role("ROLE_USER"));
        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        Set<Role> allRoles = new HashSet<>();
        adminRole.add(roleService.findById(1L));
        userRole.add(roleService.findById(2L));
        allRoles.add(roleService.findById(1L));
        allRoles.add(roleService.findById(2L));
        userService.saveUser(new User("Egor", "Bulgakov", 23L, "Admin@mail.ru", "100", adminRole));
        userService.saveUser(new User("Andrew", "Klose", 20L, "User@mail.ru", "100", userRole));
        userService.saveUser(new User("Igor", "Hoking", 27L, "Vse@mail.ru", "100", allRoles));
    }
}