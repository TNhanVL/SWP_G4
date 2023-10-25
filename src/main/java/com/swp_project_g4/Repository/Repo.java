package com.swp_project_g4.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Repo {
    @Autowired
    public UserRepository userRepository;
}
