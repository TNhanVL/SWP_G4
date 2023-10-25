package com.swp_project_g4.Repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Repo {
    private static UserRepository userRepo;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        this.userRepo = userRepository;
    }

    public static void main(String[] args) {
        var a = userRepo.findAll();
        System.out.println(a);
    }
}
