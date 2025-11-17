package app.security.JWT;

import app.domain.ports.AuthenticationPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component 
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private AuthenticationPort authenticationPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = this.extractToken(request);
            
            if (token != null) {
                this.processToken(token);
            }
        } catch (Exception e) {
            System.err.println("Error procesando token JWT: " + e.getMessage());
        }
        
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private void processToken(String token) {
        if (authenticationPort.validateToken(token)) {
            String username = authenticationPort.extractUsername(token);
            String role = authenticationPort.extractRole(token);

            if (role == null || role.trim().isEmpty()) {
                return;
            }

            String normalizedRole = role.trim().toUpperCase();
            if (!normalizedRole.startsWith("ROLE_")) {
                normalizedRole = "ROLE_" + normalizedRole;
            }

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(normalizedRole);
            
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username, 
                null, 
                Collections.singletonList(authority)
            );
            
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
}
