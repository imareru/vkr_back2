package com.example.vkr_back2.controller;

//import com.example.vkr_back2.entity.LoginDto;

import com.example.vkr_back2.entity.TeacherEntity;
import com.example.vkr_back2.resource.TeacherResource;
import com.example.vkr_back2.security.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody com.example.vkr_back2.entity.TeacherEntity getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        TeacherEntity user = (principal instanceof TeacherEntity) ? (TeacherEntity) principal : null;
        return Objects.nonNull(user) ? this.service.getByLogin(user.getT_login()) : null;

    }

}