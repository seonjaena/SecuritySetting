package com.test.sjna.security;


import com.test.sjna.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

//    private final UserAuthenticationProvider userAuthenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()

                .authorizeRequests().anyRequest().authenticated()
                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

//        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

//        http.authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll();

//        http.authenticationProvider(userAuthenticationProvider);

        /**
         * 특정 URL에서만 CSRF 토큰을 사용하도록 보장한다.
         * https://stackoverflow.com/a/29147610
         */
//        http.csrf()
//                .requireCsrfProtectionMatcher(getCsrfProtectedMatchers());

//        http.logout()
//                .logoutUrl("/logout");

        return http.build();
    }

//    @Bean
//    public RequestMatcher getCsrfProtectedMatchers()
//    {
//        return new RequestMatcher() {
//
//            private AntPathRequestMatcher[] requestMatchers = {
//                    new AntPathRequestMatcher("/api/auth/login")
//            };
//
//            @Override
//            public boolean matches(HttpServletRequest request) {
//                // 로그인 시도 URL에서만 CSRF 토큰을 사용한다.
//                for (AntPathRequestMatcher rm : requestMatchers) {
//                    if (rm.matches(request)) {
//                        return true;
//                    }
//                }
//                // 로그인 시도 URL이 아니라면 CSRF 토큰을 사용하지 않는다.
//                return false;
//            }
//        };
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns((List.of("*"))); // 허용할 URL
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE")); // 허용할 Http Method
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // 허용할 Header
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // add mapping
        return source;
    }

}
