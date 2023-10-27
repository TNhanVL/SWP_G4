/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Service;

import com.swp_project_g4.Database.AdminDAO;
import com.swp_project_g4.Database.UserDAO;
import com.swp_project_g4.Model.User;
import com.swp_project_g4.Repository.Repo;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TTNhan
 */
@Service
public class CookieServices {

    @Autowired
    private Repo repo;

    static final String learnerTokenName = "learnerJwtToken";

    public static boolean checkAdminLoggedIn(Cookie[] cookies) {
        boolean ok = false;

        try {
            String jwtToken = null;

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwtToken")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }

            Claims claims = JwtUtil.parseJwt(jwtToken);
            String username = (String) claims.get("username");
            String password = (String) claims.get("password");
            if (AdminDAO.checkAdmin(username, password, true) == 0) {
                ok = true;
            }

        } catch (Exception e) {
        }

        return ok;
    }

    public static boolean checkUserLoggedIn(Cookie[] cookies) {
        boolean ok = false;

        try {
            String jwtToken = null;

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(learnerTokenName)) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }

            Claims claims = JwtUtil.parseJwt(jwtToken);
            if (claims != null) {
                String username = (String) claims.get("username");
                String password = (String) claims.get("password");
                if (UserDAO.checkUser(username, password, true) == 0) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {

        }

        return ok;
    }

    public static boolean loginLearner(HttpServletResponse response, User user) {
        try {
            String TokenBody = JwtUtil.generateJwt(user.getUsername(), user.getPassword());
            Cookie cookie = new Cookie(learnerTokenName, TokenBody);
            cookie.setMaxAge(60 * 60 * 6);
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean logoutLearner(HttpServletRequest request, HttpServletResponse response) {
        try {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(learnerTokenName)) {
                    cookie.setValue(null);
                    response.addCookie(cookie);
                    break;
                }
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public static String getUserNameOfLearner(Cookie[] cookies) {
        String ans = "";

        try {
            String jwtToken = null;

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(learnerTokenName)) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }

            Claims claims = JwtUtil.parseJwt(jwtToken);
            ans = (String) claims.get("username");

        } catch (Exception e) {
        }

        return ans;
    }

    public static String getUserName(Cookie[] cookies) {
        String ans = "";

        try {
            String jwtToken = null;

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwtToken")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }

            Claims claims = JwtUtil.parseJwt(jwtToken);
            ans = (String) claims.get("username");

        } catch (Exception e) {
        }

        return ans;
    }
}
