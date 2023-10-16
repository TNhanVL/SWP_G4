/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Service;

import com.swp_project_g4.Model.GooglePojo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author TTNhan
 */
public class GoogleUtils {

    public static String GOOGLE_CLIENT_ID = "246255507082-vpebidclj199n0sgg035cos2ijabjrmg.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "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";
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8080/loginWithGG";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";

    public static String getToken(final String code) throws ClientProtocolException, IOException {
        String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", GOOGLE_CLIENT_ID)
                        .add("client_secret", GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", GOOGLE_GRANT_TYPE).build()
                )
                .execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }

    public static GooglePojo getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
//        System.out.println(GOOGLE_LINK_GET_USER_INFO + accessToken);
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        GooglePojo googlePojo = mapper.readValue(response, GooglePojo.class);
//        System.out.println(googlePojo);
        return googlePojo;
    }

    public UserDetails buildUser(GooglePojo googlePojo) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetail = new User(googlePojo.getEmail(),
                "", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        return userDetail;
    }

    public static void main(String[] args) {
        try {
            // Configure the Request object to use the custom SSLContext
            Request request = Request.Get("https://www.googleapis.com/oauth2/v3/userinfo?access_token=ya29.a0AbVbY6N5tmm0xlsq9WD8jd4cE83EKOhWFk1cczQPqv_5qIkeGrge-qpewKnzDePo86AM0NCnSIrM--fOcwIrMKQFl73-iMEIdzPaJDREJb2wur1AO8aeP4EfDUN2eDd_sRZs2WioWdezVg8-_YHTMBwXZIbWaCgYKAUASARISFQFWKvPlvRFJBs6SZfJxS7_lPnGBDg0163");
            
            // Execute the request
            String response = request.execute().returnContent().asString();
            
            System.out.println(response);
        } catch (IOException ex) {
            Logger.getLogger(GoogleUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
