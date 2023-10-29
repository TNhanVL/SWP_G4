/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Service;

import com.swp_project_g4.Database.AdminDAO;
import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Learner;
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
    static final String adminTokenName = "adminJwtToken";

    public static boolean checkAdminLoggedIn(Cookie[] cookies) {
        boolean ok = false;

        try {
            String jwtToken = null;

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(adminTokenName)) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }

            Claims claims = JwtUtil.parseJwt(jwtToken);
            if (claims != null) {
                String username = (String) claims.get("username");
                String password = (String) claims.get("password");
                return AdminDAO.checkAdmin(username, password, true) == 0;
            }
            return false;

        } catch (Exception e) {
        }

        return ok;
    }

    public static Cookie getResetCookie(Cookie[] cookies) {
        Cookie resetCookie = null;

        try {

            for (var cookie : cookies) {
                if (cookie.getName().equals("ResetToken"))
                    return cookie;
            }

        } catch (Exception e) {

        }

        return resetCookie;
    }

    public static boolean checkLearnerLoggedIn(Cookie[] cookies) {
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
                return LearnerDAO.checkUser(username, password, true) == 0;
            }
            return false;

        } catch (Exception e) {

        }

        return ok;
    }

    public static boolean loginLearner(HttpServletResponse response, Learner learner) {
        try {
            String TokenBody = JwtUtil.generateJwt(learner.getUsername(), learner.getPassword());
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

    public static boolean loginAdmin(HttpServletResponse response, Learner learner) {
        try {
            String TokenBody = JwtUtil.generateJwt(learner.getUsername(), learner.getPassword());
            Cookie cookie = new Cookie(adminTokenName, TokenBody);
            cookie.setMaxAge(60 * 60 * 6);
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean logoutAdmin(HttpServletRequest request, HttpServletResponse response) {
        try {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(adminTokenName)) {
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

    public static String getUserNameOfAdmin(Cookie[] cookies) {
        String ans = "";

        try {
            String jwtToken = null;

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(adminTokenName)) {
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
