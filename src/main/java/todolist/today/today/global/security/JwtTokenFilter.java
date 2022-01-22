package todolist.today.today.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        jwtTokenProvider.resolveToken(request)
                .map(jwtTokenProvider::getBody)
                .map(jwtTokenProvider::getAuthentication)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        chain.doFilter(request,response);
    }

}
