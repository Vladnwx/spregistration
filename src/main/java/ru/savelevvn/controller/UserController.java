package ru.savelevvn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.savelevvn.model.User;
import ru.savelevvn.service.AuthService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private AuthService authService;

    @GetMapping
    public ModelAndView getAllUsers() {
        log.info("Slf4j: " + this.getClass().getName() + " Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " executed");
        ModelAndView modelAndView = new ModelAndView("./user/users-list");
        List<User> users = authService.findAllUsers();
        modelAndView.addObject("users", users);
        return modelAndView;
    }

}
