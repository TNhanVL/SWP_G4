package com.swp_project_g4.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author TTNhan
 */
@Controller
@RequestMapping("/media")
public class MediaController {
    @GetMapping("")
    public String login(ModelMap model) {
//        model.addAttribute("title", "Index!");
        return "reactjs/index";
    }
}
