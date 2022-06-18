package com.jpmarket.config.auth;

import com.jpmarket.config.auth.dto.FailureHandler;
import com.jpmarket.config.jwt.AuthTokenFilter;
import com.jpmarket.config.jwt.JwtUtils;
import com.jpmarket.domain.user.Role;
import com.jpmarket.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**","/js/**","/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // TODO Role.USER  와 Role.ADMIN 구별
                .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("http://localhost:3000/")
                .and()
                .addFilterBefore(new AuthTokenFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                    .oauth2Login()
                        .failureHandler(failureHandler)
                            .successHandler(successHandler)
                                .userInfoEndpoint()
                                    .userService(customOAuth2UserService);

        http.addFilterBefore(new AuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
