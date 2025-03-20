package com.altev.ecommerce.annotations;

import com.altev.ecommerce.dao.UserRepository;
import com.altev.ecommerce.entity.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminCheckAspect {

    private final UserRepository userRepository;

    public AdminCheckAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Before("@annotation(com.altev.ecommerce.annotations.RequireAdmin)")
    public void checkAdminAccess() {
        // Get currently authenticated username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find user in database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if user is an admin
        if (!user.getIsAdmin()) {
            throw new RuntimeException("Unauthorized: Only admins can perform this action");
        }
    }
}
