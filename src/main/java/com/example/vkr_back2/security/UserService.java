package com.example.vkr_back2.security;

import com.example.vkr_back2.entity.TeacherEntity;
import com.example.vkr_back2.repository.TeacherRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    private TeacherRepository repository;

    public UserService(TeacherRepository repository) {
        this.repository = repository;
    }

    public List<TeacherEntity> getAll() {
        return List.of(this.repository.select());
    }

    public TeacherEntity getByLogin(String login) {
        return this.repository.selectByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        TeacherEntity u = getByLogin(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getT_login(), u.getT_password(), true, true, true, true, new HashSet<>());
    }
}