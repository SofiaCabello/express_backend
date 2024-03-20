package org.example.express_backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {
    public static final long EXPIRE = 1000 * 60 * 60 * 24;// token过期时间
    public static final String SECRET_KEY = "ukc8BDbRigUDaY6pZFfWus2jZWLPHOukc8BDbRigUDaY6pZFfWus2jZWLPHO";// 密钥

    /**
     * 生成token
     * @param username 用户名
     * @return token
     */
    public static String generateToken(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRE);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 校验token是否正确
     * @param token 由前端传入的token
     * @return 是否正确
     */
    public static boolean checkToken(String token){
        if(token == null || token.length() == 0){
            return false;
        }
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        }catch (Exception e){
            log.error("token校验失败");
            return false;
        }
        return true;
    }

    /**
     * 获取用户名
     * @param token 由前端传入的token
     * @return 用户名
     */
    public static String getUsername(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}

