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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
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
//        //TODO 출시 때 csrf 적용 필요
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
                .antMatchers(HttpMethod.GET, "/api/v1/pictures/**", "/api/v1/favorites/list").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
                .antMatchers(HttpMethod.GET, "/auth/user", "/auth/registrationConfirm").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/signup", "/auth/authenticate", "/api/v1/favorites").permitAll()
                .antMatchers("/", "/error",
                        "/auth/authenticate", "/auth/signup", "/oauth2/authorize/**", "/h2-console/**",
                        "/v2/**", "/swagger-ui.html", "/swagger-resources/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login() // oauth2 login handler
                .loginPage("/loginForm")
                .defaultSuccessUrl("/login-success") // redirect-url after oauth2 success
                .successHandler(successHandler)      // programmer-defined logic
                .userInfoEndpoint()                  // 로그인이 성공하면 해당 유저의 정보를 들고 customOAuth2UserService에서 후처리를 해주겠다는 의미.
                .userService(customOAuth2UserService);

    }



}
