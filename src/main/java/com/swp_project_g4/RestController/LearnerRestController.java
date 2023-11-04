package com.swp_project_g4.RestController;

import com.swp_project_g4.Repository.Repo;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.MD5;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("learner_request")
public class LearnerRestController {
    @Autowired
    private Repo repo;

    @PostMapping("change_password")
    public int changePassword(
            @RequestParam String old_password,
            @RequestParam String new_password,
            @RequestParam String username,
            HttpServletRequest req,
            HttpServletResponse res) {
        try {
            var hashed_old_password = MD5.getMd5(old_password);
            var user = repo.getLearnerRepository().findByUsername(username).orElseThrow();

            if (!user.getPassword().equals(hashed_old_password))
                return 1;

            user.setPassword(MD5.getMd5(new_password));
            repo.getLearnerRepository().save(user);

            CookieServices.logout(req, res, "learner");
            req.getSession().setAttribute("success", "Your password has been changed, please login again");
            return 0;
        } catch (Exception e) {
            return 500;
        }
    }
}
