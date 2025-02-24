package br.com.erudio.Security.Jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.erudio.Data.VO.V1.Security.TokenVO;
import br.com.erudio.Exceptions.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds; // 1h

    @Autowired
    private UserDetailsService userDetailsService;

    private Algorithm algorithm;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenVO createAccessToken(String username, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        String accessToken = generateAccessToken(username, roles, now, validity);
        String refreshToken = generateRefreshToken(username, roles, now);

        return new TokenVO(username, true, now, validity, accessToken, refreshToken);
    }

    public TokenVO refreshToken(String refreshToken) {
        String cleanToken = cleanBearerToken(refreshToken);
        DecodedJWT decodedJWT = verifyToken(cleanToken);
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        return createAccessToken(username, roles);
    }

    private String generateAccessToken(String username, List<String> roles, Date now, Date validity) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm)
                .strip();
    }

    private String generateRefreshToken(String username, List<String> roles, Date now) {
        Date validityRefreshToken = new Date(now.getTime() + (validityInMilliseconds * 3));
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validityRefreshToken)
                .withSubject(username)
                .sign(algorithm)
                .strip();
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer "))
                ? bearerToken.substring("Bearer ".length())
                : null;
    }

    public boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            return false; // Retorna false se o token for inválido ou expirado
        }
    }

    private DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    private String cleanBearerToken(String token) {
        return token.startsWith("Bearer ") ? token.substring("Bearer ".length()) : token;
    }
}
