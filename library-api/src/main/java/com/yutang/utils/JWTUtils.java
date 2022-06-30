package com.yutang.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author fzhuoxing
 * @Time 2022/3/6 4:36 下午
 * jwt工具类
 */

@ConfigurationProperties(prefix = "yutang.jwt")
@Component
@Data
public class JWTUtils {

    private Long expire;
    private String secret;
    private String header;

    //生成jwt
    public String generateToken(String username){

        Date nowDate = new Date();
        // 过期时间
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                //设置创建时间
                .setIssuedAt(nowDate)
                //7天过期
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //解析jwt
    public Claims getClaimByToken(String jwt){
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch(Exception e) {
            return null;
        }
    }

    //检测是否过期
    public boolean isTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }
}
