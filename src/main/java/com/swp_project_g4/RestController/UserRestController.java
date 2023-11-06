package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Admin;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Model.Organization;
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

    @PostMapping("auth/instructor")
    public Instructor authInstructor(HttpServletRequest request) {
        String username = CookieServices.getUserNameOfInstructor(request.getCookies());
        var instructor = repo.getInstructorRepository().findByUsername(username).orElse(null);
        return instructor;
    }

    @PostMapping("auth/organization")
    public Organization authOrganization(HttpServletRequest request) {
        String username = CookieServices.getUserNameOfOrganization(request.getCookies());
        var organization = repo.getOrganizationRepository().findByUsername(username).orElse(null);
        return organization;
    }

    @PostMapping("auth/admin")
    public Admin authAdmin(HttpServletRequest request) {
        String username = CookieServices.getUserNameOfAdmin(request.getCookies());
        var admin = repo.getAdminRepository().findByUsername(username).orElse(null);
        return admin;
    }
}
