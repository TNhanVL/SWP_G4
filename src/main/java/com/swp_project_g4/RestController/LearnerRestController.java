package com.swp_project_g4.RestController;

import com.swp_project_g4.Model.Notification;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.CookieServices;
import com.swp_project_g4.Service.MD5;
import com.swp_project_g4.Service.model.LearnerService;
import com.swp_project_g4.Service.model.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("learner_request")
public class LearnerRestController {
    @Autowired
    private LearnerService learnerService;
    @Autowired
    private NotificationService notificationService;

    @PostMapping("change_password")
    public int changePassword(
            @RequestParam String old_password,
            @RequestParam String new_password,
            @RequestParam String username,
            HttpServletRequest req,
            HttpServletResponse res) {
        try {
            var hashed_old_password = MD5.getMd5(old_password);
            var user = learnerService.getByUsername(username).orElseThrow();

            if (!user.getPassword().equals(hashed_old_password))
                return 1;

            user.setPassword(MD5.getMd5(new_password));
            learnerService.save(user);

            CookieServices.logout(req, res, "learner");
            req.getSession().setAttribute("success", "Your password has been changed, please login again");
            return 0;
        } catch (Exception e) {
            return 500;
        }
    }

    @PostMapping("notification")
    public List<Notification> getNotification(@RequestParam String id_string) {
        List<Notification> returnList = null;
        try {
            var id = Integer.parseInt(id_string);
            returnList = notificationService.getAllByLearnerId(id);
        } catch (Exception e) {
            System.err.println(e);
        }
        return returnList;
    }
}
