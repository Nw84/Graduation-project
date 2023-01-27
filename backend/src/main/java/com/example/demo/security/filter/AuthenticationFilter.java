package com.example.demo.security.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.User;
import com.example.demo.security.SecurityConstants;
import com.example.demo.security.manager.CustomAuthenicationManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    @Autowired
    private CustomAuthenicationManager authenticationManager;

    public AuthenticationFilter(CustomAuthenicationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    @CrossOrigin
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
                {
                try {
                    User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
                    return authenticationManager.authenticate(authentication);  
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }

            @Override
            protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException failed) throws IOException, ServletException {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write(failed.getMessage());
                        response.getWriter().flush();
            }


            @Override
            protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                Authentication authResult) throws IOException, ServletException {
                    String token = JWT.create()
                        .withSubject(authResult.getName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                        .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
                        response.addHeader((SecurityConstants.AUTHORIZATION), SecurityConstants.BEARER + token);
                        //Cookie httpOnlyCookie = new Cookie(token, token);
                        //httpOnlyCookie.setHttpOnly(true);
                        //response.addCookie(httpOnlyCookie);
            }

    }