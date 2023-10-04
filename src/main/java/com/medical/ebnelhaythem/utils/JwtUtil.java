package com.medical.ebnelhaythem.utils;

import java.util.Arrays;
import java.util.List;
import com.medical.ebnelhaythem.config.JwtYmlPropertiesConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
@AllArgsConstructor
public class JwtUtil {


    private JwtYmlPropertiesConfig ymlPropertiesConfig;


    // generate token for user
    /*
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            claims.put("isAdmin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            claims.put("isUser", true);
        }
        return doGenerateToken(claims, userDetails.getUsername());
    }
     */

    /*
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

     */
/*
    public boolean validateToken(String authToken) {
        try {
            // Jwt token has not been tampered with
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {s
            throw new ex(header, claims, "Token has Expired", ex);
        }
    }

 */

    public String getUsernameFromToken(String token) {
        token = token.replace("Bearer","");
        Claims claims = Jwts.parser().setSigningKey(ymlPropertiesConfig.getSecret().getBytes()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String authToken) {
        authToken = authToken.replace("Bearer","");
        List<SimpleGrantedAuthority> roles = null;
        Claims claims = Jwts.parser().setSigningKey(ymlPropertiesConfig.getSecret().getBytes()).parseClaimsJws(authToken).getBody();
        Boolean isAdmin = claims.get("isAdmin", Boolean.class);
        Boolean isUser = claims.get("isUser", Boolean.class);
        if (isAdmin != null && isAdmin == true) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (isUser != null && isUser == true) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return roles;
    }


    public Integer getCliniqueId(String token) {
        token = token.replace("Bearer","");
        Claims claims = Jwts.parser().setSigningKey(ymlPropertiesConfig.getSecret().getBytes()).parseClaimsJws(token).getBody();
        return claims.get("cliniqueId", Integer.class);
    }


}