package com.mekaku.ppic.util;

import com.mekaku.ppic.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
//JWT
@Component
public class JwtUtil {
    @Value("098f6bcd4621d373cade4e832627b4f6")
    private String clientId;
    @Value("MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=")
    private String base64Secret;
    @Value("restapiuser")
    private String name;
    @Value("86400000")
    private Long expiresSecond;

    //解析jwt
    public UserInfo parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jsonWebToken).getBody();
            if (claims == null) {
                return null;
            }
            int id = (int) claims.get("id");
            String name = (String) claims.get("name");
            UserInfo user = new UserInfo();
            user.setUid(id);
            user.setName(name);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    //构建jwt
    public String createJWT(UserInfo user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("id", user.getUid())
                .claim("name", user.getName())
                .setIssuer(clientId)
                .setAudience(name)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + expiresSecond;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(now);
        //生成JWT
        return builder.compact();
    }
}
