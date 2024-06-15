//package com.test.sjna.security;
//
//import com.test.sjna.exception.UserNotFoundException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//@Slf4j
//@Component
//public class UserAuthenticationProvider implements AuthenticationProvider {
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        UsernamePasswordAuthenticationToken result = null;
//
//        String userId = request.getParameter("sui");
//        String userPw = request.getParameter("sup");
//
//        log.debug("USER_ID = [{}]", userId);
//
//        try {
//            if(!Objects.equals(userId, "test-user")) {
//                throw new UserNotFoundException("User Not Found. USER_ID = [" + userId + "]");
//            }
//
//            if(!Objects.equals(userPw, "test-user-pw")) {
//                throw new BadCredentialsException("Password Incorrect. USER_ID = [" + userId + "]");
//            }
//        } catch (UserNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
//        roles.add(new SimpleGrantedAuthority("USER"));
//
//        result = new UsernamePasswordAuthenticationToken(
//                userId, userPw, roles);
//        result.setDetails(new CustomDetails(userId, userPw));
//
//        return result;
//    }
//}
