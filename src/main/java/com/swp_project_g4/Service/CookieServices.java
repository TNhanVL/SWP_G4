/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Service;

import com.swp_project_g4.Database.AdminDAO;
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

    private static Repo repo;

    @Autowired
    public CookieServices(Repo repo) {
        CookieServices.repo = repo;
    }

    public enum TokenName {
        LEARNER("learnerJwtToken"),
        ADMIN("adminJwtToken"),
        ORGANIZATION("organizationJwtToken"),
        INSTRUCTOR("instructorJwtToken");

        private final String text;

        TokenName(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static String searchCookie(Cookie[] cookies, TokenName token) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(token.toString()))
                return cookie.getValue();
        }
        return null;
    }


    public static boolean checkAdminLoggedIn(Cookie[] cookies) {
        boolean ok = false;

        try {
            String jwtToken = searchCookie(cookies, TokenName.ADMIN);

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

    public static boolean checkLearnerLoggedIn(Cookie[] cookies) {
        boolean ok = false;

        try {
            String jwtToken = searchCookie(cookies, TokenName.LEARNER);

            Claims claims = JwtUtil.parseJwt(jwtToken);
            if (claims != null) {
                String username = (String) claims.get("username");
                String password = (String) claims.get("password");
                var instructorOptional = repo.getLearnerRepository().findByUsername(username);
                return instructorOptional.isPresent() && instructorOptional.get().getPassword().equals(password);
            }
            return false;

        } catch (Exception e) {

        }

        return ok;
    }

    public static boolean checkInstructorLoggedIn(Cookie[] cookies) {
        boolean ok = false;

        try {
            String jwtToken = searchCookie(cookies, TokenName.INSTRUCTOR);

            Claims claims = JwtUtil.parseJwt(jwtToken);
            if (claims != null) {
                String username = (String) claims.get("username");
                String password = (String) claims.get("password");
                var instructorOptional = repo.getInstructorRepository().findByUsername(username);
                return instructorOptional.isPresent() && instructorOptional.get().getPassword().equals(password);
            }
            return false;

        } catch (Exception e) {

        }

        return ok;
    }

    public static Cookie[] getResetCookie(Cookie[] cookies) {
        Cookie[] resetCookie = new Cookie[2];

        try {

            for (var cookie : cookies) {
                if (cookie.getName().equals("ResetToken")) {
                    resetCookie[0] = cookie;
                    continue;
                }

                if (cookie.getName().equals("ResetID")) {
                    resetCookie[1] = cookie;
                }
            }

        } catch (Exception e) {

        }

        return resetCookie;
    }

    public static boolean loginAccount(HttpServletResponse response, String username, String password, TokenName token) {
        try {
            String TokenBody = JwtUtil.generateJwt(username, password);
            Cookie cookie = new Cookie(token.toString(), TokenBody);
            cookie.setMaxAge(60 * 60 * 6);
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean logout(HttpServletRequest request, HttpServletResponse response, TokenName token) {
        try {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(token.toString())) {
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
            String jwtToken = searchCookie(cookies, TokenName.LEARNER);


            Claims claims = JwtUtil.parseJwt(jwtToken);
            ans = (String) claims.get("username");

        } catch (Exception e) {
        }

        return ans;
    }

    public static String getUserNameOfInstructor(Cookie[] cookies) {
        String ans = "";

        try {
            String jwtToken = searchCookie(cookies, TokenName.INSTRUCTOR);

            Claims claims = JwtUtil.parseJwt(jwtToken);
            ans = (String) claims.get("username");

        } catch (Exception e) {
        }

        return ans;
    }

    public static String getUserNameOfAdmin(Cookie[] cookies) {
        String ans = "";

        try {
            String jwtToken = searchCookie(cookies, TokenName.ADMIN);

            Claims claims = JwtUtil.parseJwt(jwtToken);
            ans = (String) claims.get("username");

        } catch (Exception e) {
        }

        return ans;
    }
}
