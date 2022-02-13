package todolist.today.today.global.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import todolist.today.today.global.security.exception.InvalidTokenException;
import todolist.today.today.global.security.auth.AuthDetailsService;
import todolist.today.today.global.security.service.properties.JwtProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final AuthDetailsService authDetailsService;

    public String generateAccessToken(String id) {
        return makingToken(id, "access", jwtProperties.getAccessExp());
    }

    public String generateRefreshToken(String id) {
        return makingToken(id, "refresh", jwtProperties.getRefreshExp());
    }

    private String makingToken(String id, String type, Long time){
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + (time * 1000L)))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .setIssuedAt(new Date())
                .setSubject(id)
                .claim("type", type)
                .compact();
    }

    public Optional<String> resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    public Authentication getAuthentication(Claims body) {
        if (!isAccess(body)) {
            throw new InvalidTokenException();
        }
        UserDetails details = authDetailsService.loadUserByUsername(getId(body));
        return new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities());
    }

    public Claims getBody(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    private boolean isAccess(Claims body) {
        return body.get("type", String.class).equals("access");
    }

    public boolean isRefresh(String token) {
        Claims body = getBody(token);
        return body.get("type", String.class).equals("refresh");
    }

    public String getId(Claims body) {
        return body.getSubject();
    }


}
