package com.jpmarket.config.auth;

import com.jpmarket.config.auth.dto.FailureHandler;
import com.jpmarket.config.jwt.AuthTokenFilter;
import com.jpmarket.config.jwt.CorsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/content/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/h2-console/**");
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(customPasswordEncoder());
    }

    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(4));
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                System.out.println("raw: " + rawPassword + " encoded: " + encodedPassword);
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        };
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public AuthTokenFilter tokenAuthenticationFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public CorsFilter simpleCorsFilter() {
        return new CorsFilter();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().headers().frameOptions().disable().and()
//                .authorizeRequests()
//                .antMatchers("/", "/css/**", "/images/**","/js/**","/h2-console/**").permitAll()
//                .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // TODO Role.USER  와 Role.ADMIN 구별
//                .anyRequest().authenticated()
//                .and()
//                    .logout()
//                        .logoutSuccessUrl("http://localhost:3000/")
//                .and()
//                .addFilterBefore(new AuthTokenFilter(),
//                        UsernamePasswordAuthenticationFilter.class)
//                    .oauth2Login()
//                        .failureHandler(failureHandler)
//                            .successHandler(successHandler)
//                                .userInfoEndpoint()
//                                    .userService(customOAuth2UserService);
//
//        //TODO 시큐리티 체크. 포스팅시 오류 떠서 적음. 무슨 뜻인지 모름
//        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
//
//        http.addFilterBefore(new AuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        http.cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .addFilterBefore(simpleCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
                .antMatchers("/", "/error", "/api/authenticate/**", "/api/register",
                        "/auth/authenticate", "/auth/signup", "/oauth2/**", "/h2-console/**",
                        "/v2/**", "/swagger-ui.html", "/swagger-resources/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(new HttpCookieOAuth2AuthorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(successHandler)
                .failureHandler(failureHandler);

    }

}
