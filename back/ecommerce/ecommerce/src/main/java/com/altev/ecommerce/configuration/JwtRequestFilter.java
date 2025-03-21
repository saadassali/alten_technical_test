package com.altev.ecommerce.configuration;

import com.altev.ecommerce.service.impl.CustomUserDetailsService;
import com.altev.ecommerce.service.impl.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    private final JwtTokenService jwtTokenService;
    private final CustomUserDetailsService userDetailsService;

    public JwtRequestFilter(JwtTokenService jwtTokenService, CustomUserDetailsService userDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtTokenService.validateTokenAndGetUsername(token);
            if (username != null) {
                // Token is valid, load user details from DB
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Create authentication token
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set user in Spring Security context
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        // continue filter chain
        chain.doFilter(request, response);
    }
}
