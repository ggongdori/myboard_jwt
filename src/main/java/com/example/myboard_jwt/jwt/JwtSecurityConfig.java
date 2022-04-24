//package com.example.myboard_jwt.jwt;
//
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    private JwtTokenUtils jwtTokenUtils;
//
//    public JwtSecurityConfig(JwtTokenUtils jwtTokenUtils) {
//        this.jwtTokenUtils = jwtTokenUtils;
//    }
//
//    @Override
//    public void configure(HttpSecurity http) {
//        JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter(jwtTokenUtils);
//        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//}