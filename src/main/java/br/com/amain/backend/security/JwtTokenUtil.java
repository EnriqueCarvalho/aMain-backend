package br.com.amain.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenUtil {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    //private static final long JWT_EXPIRATION = 30 * 60 * 1000; //30 min
    //private static final long JWT_EXPIRATION = 365 * 24 * 60 * 60 * 1000; //1 ano 
    private static final int JWT_REFRESH_TOKEN_LENGTH = 32;
    private static final String JWT_REFRESH_TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789#@$?&!+=-";

    private final Key JWT_SECRET_KEY;

    public JwtTokenUtil(@Value("${api.jwt.secret}") String jwtSecret) {
        JWT_SECRET_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication, Long idUsuario, String login ) {
        return generateToken(authentication.getName(), idUsuario, login);
    }

    public String generateToken(String username, Long idUsuario, String login) {
        LOGGER.log(Level.INFO, "Gerando token para o usuário: " + username);
        long currentTimestampInMillis = System.currentTimeMillis();
        final Map<String, Object> claims = new HashMap<>();

        claims.put("idUsuario", idUsuario);
        String token = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username.trim())
                .setIssuedAt(new Date(currentTimestampInMillis))
               // .setExpiration(new Date(currentTimestampInMillis + JWT_EXPIRATION))
                .signWith(JWT_SECRET_KEY)
                .compact();

        return token;
    }

    public String generateRefreshToken() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < JWT_REFRESH_TOKEN_LENGTH; i++) {
            int position = (int) Math.floor(Math.random() * JWT_REFRESH_TOKEN_CHARS.length());
            result.append(JWT_REFRESH_TOKEN_CHARS.charAt(position));
        }
        return result.toString();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Long getIdUsuarioFromToken(String token) {
        return (long) Math.round((float) getClaimFromToken(token, claims -> claims.get("idUsuario", Object.class)));
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return StringUtils.hasText(username) && !isTokenExpired(token);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        // final Date expiration = getExpirationDateFromToken(token);
        // return expiration.before(new Date());
        return false;
    }

    public String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
