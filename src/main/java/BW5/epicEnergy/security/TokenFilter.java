package BW5.epicEnergy.security;

import BW5.epicEnergy.entity.Utente;
import BW5.epicEnergy.exception.UnauthorizedException;
import BW5.epicEnergy.service.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


@Component
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TokenTools tokenTools;
    private final UtenteService utenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Invalid token supplied in the authorization header");
        String accessToken = authorizationHeader.replace("Bearer ", "");
        tokenTools.verifyToken(accessToken);
        UUID userId = this.tokenTools.extractFromToken(accessToken);
        Utente authenticatedUser = this.utenteService.findById(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/**", request.getServletPath());
    }

    
}
