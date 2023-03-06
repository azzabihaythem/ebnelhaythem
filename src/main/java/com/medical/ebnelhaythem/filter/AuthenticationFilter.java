package com.medical.ebnelhaythem.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.medical.ebnelhaythem.controller.UserController;
import com.medical.ebnelhaythem.service.UserService;
import com.medical.ebnelhaythem.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

       private UserService userService;
    private UserController userController = new UserController();
        private AuthenticationManager authenticationManager;
        public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
                this.userService = userService ;
                this.authenticationManager = authenticationManager;
                setFilterProcessesUrl("/login");

        }


        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
                try {
                        com.medical.ebnelhaythem.entity.User creds = new ObjectMapper().readValue(request.getInputStream(), com.medical.ebnelhaythem.entity.User.class);
                        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getLogin(), creds.getPassword(),new ArrayList<>()));
                }
                catch(IOException e) {
                        throw new RuntimeException("Could not read request" + e);
                }
        }

        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException {
                String token = Jwts.builder()
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                      //.claim("role", user.getRole().toString())
                        //todo ligne to update
                        .claim("role", userService.findByLogin(((User) authentication.getPrincipal()).getUsername()).getRoles().get(0).getRole())
                        .claim("role", userService.findByLogin(((User) authentication.getPrincipal()).getUsername()).getRoles().get(0).getId())
                        .claim("userId", userService.findByLogin(((User) authentication.getPrincipal()).getUsername()).getId())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS512, Constants.SECRET_KEY_TO_GEN_JWTS.getBytes())//todo update the value of the SecretKeyToGenJWTs
                .compact();
            //response.addHeader("Access-Control-Allow-Headers", "Authorization");
            //response.addHeader("access-control-expose-headers", "Set-Cookie");
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Authorization", token);
                //response.addHeader("Authorization","Bearer " + token);
            String content = "{touken:"+token+"}";
            //String json = new Gson().toJson(content);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
/*

            JsonObject json = new JsonObject();

            // put some value pairs into the JSON object .
            json.addProperty("touken", token);
            response.getWriter().write(json.toString());
            // finally output the json string
            PrintWriter out = response.getWriter();
            out.print(json.toString());
            out.flush(); */
        }




/*
https://blog.softtek.com/en/token-based-api-authentication-with-spring-and-jwt
        private String getJWTToken(String username) {
                String secretKey = "mySecretKey";
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                        .commaSeparatedStringToAuthorityList("ROLE_USER");

                String token = Jwts
                        .builder()
                        .setId("softtekJWT")
                        .setSubject(username)
                        .claim("authorities",
                                grantedAuthorities.stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()))
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 600000))
                        .signWith(SignatureAlgorithm.HS512,
                                secretKey.getBytes()).compact();

                return "Bearer " + token;
        }
 */


}