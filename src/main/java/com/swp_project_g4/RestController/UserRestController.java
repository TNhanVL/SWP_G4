package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.CookieServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:3000/"}, allowCredentials = "true")
@RestController
@RequestMapping("api")
public class UserRestController {
    @Autowired
    private Repo repo;

    @PostMapping("auth/learner")
    public Learner authLearner(HttpServletRequest request) {
        String username = CookieServices.getUserNameOfLearner(request.getCookies());
        var learner = repo.getLearnerRepository().findByUsername(username).orElse(null);
        return learner;
    }
}
