package scau.pdc.demo.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    private static final String SECRET_KEY = "RJHmWbhC1Fg9ZvzMQKY2CeAs4gAQ5wpzRJHmWbhC1Fg9ZvzMQKY2CeAs4gAQ5wpz";

    private static final String ISSUER = "PDC";

    private static final String COOKIE_NAME = "access_token";

    private static SecretKey secretKey;

    /**
     * 生成JWT令牌
     *
     * @param subject     JWT的主题，通常为用户ID或其他唯一标识符
     * @param extraClaims 额外的JWT声明，可以包含任何键值对信息
     * @return 返回生成的JWT令牌字符串
     */
    public static String generateJwtToken(String subject, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .subject(subject)
                .claims(extraClaims)
                .issuer(ISSUER)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(getSecretKey())
                .compact();
    }

    public static String generateJwtToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuer(ISSUER)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(getSecretKey())
                .compact();
    }

    public static boolean verifyJwtToken(String token, String userSubject) {
        String subject = getSubjectFromJwtToken(token);
        return StringUtils.hasText(subject) && subject.equals(userSubject) && !isTokenExpired(token);
    }

    public static String getSubjectFromJwtToken(String token) {
        return getClaimFromJwtToken(token, Claims::getSubject);
    }

    public static boolean isTokenExpired(String token) {
        return getClaimFromJwtToken(token, Claims::getExpiration).before(new Date());
    }

    public static <T> T getClaimFromJwtToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaimsFromJwtToken(token);
        return claimsResolver.apply(claims);
    }

    public static Claims getClaimsFromJwtToken(String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    public static String getJwtTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    private static SecretKey getSecretKey() {
        if (secretKey == null) {
            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
            secretKey = Keys.hmacShaKeyFor(keyBytes);
        }
        return secretKey;
    }
}
