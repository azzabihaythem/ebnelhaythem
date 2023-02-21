package com.medical.ebnelhaythem.filter;

import com.medical.ebnelhaythem.config.JwtYmlPropertiesConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private JwtYmlPropertiesConfig ymlPropertiesConfig;

    public AuthorizationFilter(AuthenticationManager authenticationManager, JwtYmlPropertiesConfig ymlPropertiesConfig) {
        super(authenticationManager);
        this.ymlPropertiesConfig = ymlPropertiesConfig;

        }


    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
            String header = request.getHeader("Authorization");
            if(header == null || !header.startsWith("Bearer"))  {
                filterChain.doFilter(request,response);
            return;
            }
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token != null) {
            String user = Jwts.parser().setSigningKey(ymlPropertiesConfig.getSecret().getBytes())
            .parseClaimsJws(token.replace("Bearer",""))
            .getBody()
            .getSubject();
            if(user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }

            return null;
        }
}
