package ohchangmin.sns.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isJwtAuthorization(authHeader)) {
            String token = getToken(authHeader);
            setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isJwtAuthorization(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    private String getToken(String authHeader) {
        return authHeader.split(" ")[1];
    }

    private void setAuthentication(String token) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        log.info("SecurityContext에 {}가 등록 되었습니다.", authentication.getName());
    }
}