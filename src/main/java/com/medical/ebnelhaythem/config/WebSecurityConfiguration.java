package com.medical.ebnelhaythem.config;

import com.medical.ebnelhaythem.filter.AuthenticationFilter;
import com.medical.ebnelhaythem.filter.AuthorizationFilter;
import com.medical.ebnelhaythem.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


  //  private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private UserDetailsService userDetailsService;
    private JwtYmlPropertiesConfig ymlPropertiesConfig;

    private static final String[] AUTH_WHITELIST = {
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**"
        };

    public WebSecurityConfiguration(UserDetailsService userDetailsService, UserService userService,
                                    JwtYmlPropertiesConfig ymlPropertiesConfig
                      //              ,BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
     //   this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService ;
        this.userDetailsService = userDetailsService;
        this.ymlPropertiesConfig = ymlPropertiesConfig;
        }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
                .and()
                    .csrf().disable().authorizeRequests()
                        .antMatchers(AUTH_WHITELIST)
                            .permitAll()
                        .antMatchers(HttpMethod.POST, "/v1/signup")
                            .permitAll()
                        .antMatchers(HttpMethod.POST, "/v1/clinique")
                            .permitAll()
                        .anyRequest()
                            .authenticated()
                .and()
                    .addFilter(new AuthenticationFilter(authenticationManager(), userService,ymlPropertiesConfig))
                    .addFilter(new AuthorizationFilter(authenticationManager(),ymlPropertiesConfig))
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

   /* public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        }

    */

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
            return source;
        }



    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


}
