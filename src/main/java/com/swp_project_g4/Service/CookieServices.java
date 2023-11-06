/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Service;

import com.swp_project_g4.Database.AdminDAO;
import com.swp_project_g4.Database.InstructorDAO;
import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Instructor;
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


    public static Claims searchCookie(Cookie[] cookies, CookiesToken token) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(token.toString()))
                return JwtUtil.parseJwt(cookie.getValue());
        }

        return null;
    }

    public Claims searchCookies(Cookie[] cookies, String token) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(token))
                return JwtUtil.parseJwt(cookie.getValue());
        }

        return null;
    }

    public String getUsername(Cookie[] cookies, String token) {
        return (String) searchCookies(cookies, token).get("username");
    }

    public boolean checkLogin(Cookie[] cookies, CookiesToken type) {

        try {

            var cookie = searchCookies(cookies, type.toString());
            var username = cookie.get("username").toString();
            var password = cookie.get("password").toString();

            switch (type) {
                case LEARNER -> {
                    return repo.getLearnerRepository().findByUsernameAndPassword(username, password).isPresent();
                }
                case ADMIN -> {
                    return repo.getAdminRepository().findByUsernameAndPassword(username, password).isPresent();
                }
                case ORGANIZATION -> {
                    return repo.getOrganizationRepository().findByUsernameAndPassword(username, password).isPresent();
                }
                case INSTRUCTOR -> {
                    return repo.getInstructorRepository().findByUsernameAndPassword(username, password).isPresent();
                }
                case RESET -> {
                    return false;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean checkAdminLoggedIn(Cookie[] cookies) {
        boolean ok = false;

        try {
            Claims claims = searchCookie(cookies, CookiesToken.ADMIN);

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
            Claims claims = searchCookie(cookies, CookiesToken.LEARNER);

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

    public static boolean checkInstructorLoggedIn(Cookie[] cookies) {
        boolean ok = false;

        try {
            Claims claims = searchCookie(cookies, CookiesToken.INSTRUCTOR);

            if (claims != null) {
                String username = (String) claims.get("username");
                String password = (String) claims.get("password");
                Instructor instructor = InstructorDAO.getInstructorByUsername(username);
                return instructor.getPassword().equals(password);
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

    public static boolean loginAccount(HttpServletResponse response, String username, String password, CookiesToken token) {
        try {
            String TokenBody = JwtUtil.generateJwt(username, password, token);
            Cookie cookie = new Cookie(token.toString(), TokenBody);
            cookie.setMaxAge(60 * 60 * 6);
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean logout(HttpServletRequest request, HttpServletResponse response, String token) {
        try {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(token + "JwtToken")) {
                    cookie.setMaxAge(0);
                    cookie.setValue(null);
                    response.addCookie(cookie);
                }
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean checkLoggedIn(HttpServletRequest request) {
        for (var cookie : request.getCookies()) {
            if (!cookie.getName().equals("JSESSIONID") && !cookie.getName().equals(CookiesToken.RESET.toString()))
                return true;
        }

        return false;
    }


    public static String getUserNameOfLearner(Cookie[] cookies) {
        String ans = "";

        try {

            Claims claims = searchCookie(cookies, CookiesToken.LEARNER);

            ans = (String) claims.get("username");

        } catch (Exception e) {
        }

        return ans;
    }

    public static String getUserNameOfInstructor(Cookie[] cookies) {
        String ans = "";

        try {
            Claims claims = searchCookie(cookies, CookiesToken.INSTRUCTOR);

            ans = (String) claims.get("username");

        } catch (Exception e) {
        }

        return ans;
    }

    public static String getUserNameOfAdmin(Cookie[] cookies) {
        String ans = "";

        try {

            Claims claims = searchCookie(cookies, CookiesToken.ADMIN);
            ans = (String) claims.get("username");

        } catch (Exception e) {
        }

        return ans;
    }
}
