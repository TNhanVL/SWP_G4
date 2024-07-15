package com.swp_project_g4.Controller;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Learner;
import com.swp_project_g4.Service.MD5;
import com.swp_project_g4.Service.model.LearnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Service
@RequestMapping("/s")
public class ShortLinkController {
    @Autowired
    private LearnerService learnerService;

    @RequestMapping(value = "/{linkId}", method = RequestMethod.GET)
    public String getShortLink(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int linkId) {
        switch (linkId) {
            case 1:
                return "redirect:https://lanhstore.mystrikingly.com/blog/long-nguyen-h-ng-v-ng-t-t-d-ng-b-ng-song-c-u-long";
            case 2:
                return "redirect:https://lanhstore.mystrikingly.com/blog/chuy-n-hanh-trinh-c-a-tra-chanh-day-kim-qu-t";
            case 3:
                return "redirect:https://lanhstore.mystrikingly.com/blog/tra-kh-om-h-ng-v-t-thien-nhien-tinh-tuy-trong-t-ng-gi-t-tra";
        }
        return "redirect:https://lanhstore.mystrikingly.com";
    }
}