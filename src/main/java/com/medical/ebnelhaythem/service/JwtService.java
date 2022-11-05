package com.medical.ebnelhaythem.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;


@Component
public class JwtService {


    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean verificationtoken(String token) throws Exception {
        token = token.substring(7);

        boolean verifiedconnected = isTokenExpired(token);
        if (!verifiedconnected) {
            return true;
        } else {
            throw new Exception("Token has Expired");
        }
    }
}
