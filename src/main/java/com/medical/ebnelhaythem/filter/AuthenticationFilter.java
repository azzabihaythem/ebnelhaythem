package com.medical.ebnelhaythem.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.ebnelhaythem.config.JwtYmlPropertiesConfig;
import com.medical.ebnelhaythem.service.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.jsonwebtoken.Jwts;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


        private UserService userService;

        private JwtYmlPropertiesConfig ymlPropertiesConfig;

        private AuthenticationManager authenticationManager;

        public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService,
                                    JwtYmlPropertiesConfig ymlPropertiesConfig) {
                this.userService = userService ;
                this.authenticationManager = authenticationManager;
                this.ymlPropertiesConfig = ymlPropertiesConfig;

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

        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
                String token = Jwts.builder()
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                      //.claim("role", user.getRole().toString())
                        //todo ligne to update
                        .claim("role", userService.findByLogin(((User) authentication.getPrincipal()).getUsername()).getRoles().get(0).getRole())
                        .claim("cliniqueId", userService.findByLogin(((User) authentication.getPrincipal()).getUsername()).getClinique().getId())
                .setExpiration(new Date(System.currentTimeMillis() + ymlPropertiesConfig.getExpirationDateInMs()))
                .signWith(SignatureAlgorithm.HS512, ymlPropertiesConfig.getSecret().getBytes())
                .compact();


                response.addHeader("Authorization","Bearer " + token);

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