package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Admin;
import com.swp_project_g4.Model.Instructor;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Model.Organization;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:3000/"}, allowCredentials = "true")
@RestController
@RequestMapping("api")
public class UserRestController {
    @Autowired
    private LearnerService learnerService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private AdminService adminService;

    @PostMapping("auth/learner")
    public Learner authLearner(HttpServletRequest request) {
        String username = CookieServices.getUserNameOfLearner(request.getCookies());
        var learner = learnerService.getByUsername(username).orElse(null);
        return learner;
    }

    @PostMapping("auth/instructor")
    public Instructor authInstructor(HttpServletRequest request) {
        String username = CookieServices.getUserNameOfInstructor(request.getCookies());
        var instructor = instructorService.getByUsername(username).orElse(null);
        return instructor;
    }

    @PostMapping("auth/organization")
    public Organization authOrganization(HttpServletRequest request) {
        String username = CookieServices.getUserNameOfOrganization(request.getCookies());
        var organization = organizationService.getByUsername(username).orElse(null);
        return organization;
    }

    @PostMapping("auth/admin")
    public Admin authAdmin(HttpServletRequest request) {
        String username = CookieServices.getUserNameOfAdmin(request.getCookies());
        var admin = adminService.getByUsername(username).orElse(null);
        return admin;
    }
}
