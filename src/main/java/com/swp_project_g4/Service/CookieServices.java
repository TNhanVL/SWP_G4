/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Service;

import com.swp_project_g4.Database.AdminDAO;
import com.swp_project_g4.Database.UserDAO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;

/**
 *
 * @author TTNhan
 */
public class CookieServices {

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
                if (cookie.getName().equals("jwtToken")) {
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
