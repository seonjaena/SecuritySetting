package com.test.sjna.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/view/login").permitAll();

        /**
         * 특정 URL에서만 CSRF 토큰을 사용하도록 보장한다.
         * https://stackoverflow.com/a/29147610
         */
        http.csrf()
                .requireCsrfProtectionMatcher(getCsrfProtectedMatchers());

        http.formLogin(login -> login
                        .loginPage("/view/login")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("userid")
                        .passwordParameter("pw")
                        .defaultSuccessUrl("/view/dashboard", true)
                        .permitAll()
                )
                .logout(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public RequestMatcher getCsrfProtectedMatchers()
    {
        return new RequestMatcher() {

            private AntPathRequestMatcher[] requestMatchers = {
                    new AntPathRequestMatcher("/view/start")
            };

            @Override
            public boolean matches(HttpServletRequest request) {
                // 로그인 시도 URL에서만 CSRF 토큰을 사용한다.
                for (AntPathRequestMatcher rm : requestMatchers) {
                    if (rm.matches(request)) {
                        return true;
                    }
                }
                // 로그인 시도 URL이 아니라면 CSRF 토큰을 사용하지 않는다.
                return false;
            }
        };
    }

}
