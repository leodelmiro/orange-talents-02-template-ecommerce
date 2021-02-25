package com.leodelmiro.mercadolivre.common.security;

import com.leodelmiro.mercadolivre.newuser.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private TokenManager tokenManager;
    private UsersService usersService;

    public JwtAuthenticationFilter(TokenManager tokenManager, UsersService usersService) {
        this.tokenManager = tokenManager;
        this.usersService = usersService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String possibleToken = getTokenFromRequest(request);
        boolean isValid = tokenManager.isValid(possibleToken);

        if (isValid) {
            authenticateUser(possibleToken);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token) {
        String userEmail = tokenManager.getUserName(token);
        User user = (User) usersService.loadUserByUsername(userEmail);

        if (user == null) {
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");

        if (authToken == null || authToken.isEmpty()) {
            return null;
        }

        return authToken;
    }
}
