package com.swp_project_g4.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Repo {
    @Autowired
    private UserRepository userRepository;

    public UserRepository getUserRepo() {
        return userRepository;
    }
}
