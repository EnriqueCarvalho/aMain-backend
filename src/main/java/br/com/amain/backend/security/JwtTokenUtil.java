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

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.amain.backend.exception.DadosInvalidosException;




@Component
public class JwtTokenUtil {

    private static final int JWT_REFRESH_TOKEN_LENGTH = 32;
    private static final String JWT_REFRESH_TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789#@$?&!+=-";
    private static final long JWT_EXPIRATION = 300 * 60 * 1000; //5 horas
    private static final String KEY_STRING = "!2PxhMh%@4I^0nF!qosW+*Y0if@prM$u7Q";

    private final Key JWT_SECRET_KEY;

    public JwtTokenUtil() {
        JWT_SECRET_KEY = Keys.hmacShaKeyFor(KEY_STRING.getBytes());
    }

    public String generateToken(Authentication authentication, Long idPessoaCli) {
        return generateToken(authentication.getName(), idPessoaCli);
    }

    public String generateToken(String username, Long idUsuario){
        long currentTimestampInMillis = System.currentTimeMillis();
        final Map<String, Object> claims = new HashMap<>();

        claims.put("idUsuario", idUsuario);
        String token = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username.trim())
                .setIssuedAt(new Date(currentTimestampInMillis))
                .setExpiration(new Date(currentTimestampInMillis + JWT_EXPIRATION))
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

    public Long getIdPessoaCliFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("idPessoaCli", Double.class)).longValue();
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
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public Long getIdPessoaCliFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7, bearerToken.length());            
            return getClaimFromToken(token, claims -> claims.get("idUsuario", Double.class)).longValue();
        }
        throw new DadosInvalidosException("Não foi possível extrair o idUsuario");
    }


    public String getClaimStringFromToken(String token, String key) {
        return getClaimFromToken(token, claims -> claims.get(key, String.class));
    }
}
