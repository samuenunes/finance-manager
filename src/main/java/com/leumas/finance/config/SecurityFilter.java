package com.leumas.finance.config;

import com.leumas.finance.config.db.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(Strings.isNotEmpty(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Optional<JWTUserData> jwtUserData = tokenService.verifyToken(token);
            if(jwtUserData.isPresent()){
                JWTUserData userData = jwtUserData.get();
                TenantContext.setCurrentTenant(String.valueOf(userData.id()));
                SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                        userData,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_"+userData.role()))
                    )
                );
            }
        }
        filterChain.doFilter(request, response);
    }
}
