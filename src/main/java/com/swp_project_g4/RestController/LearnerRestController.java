package com.swp_project_g4.RestController;

import com.swp_project_g4.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("learner_request")
public class LearnerRestController {
    @Autowired
    private Repo repo;


    @GetMapping("change_password")
    public String changePassword() {
        return "dkm truong 3 chu";
    }
}
