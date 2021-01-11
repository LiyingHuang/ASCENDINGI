package org.liying.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.liying.model.Role;
import org.liying.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JWTService {
    private final String SECRET_KEY = System.getProperty("secret.key");
    // SECRET_KEY at least 4 numbers or letters or Error: empty key
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final String ISSUER ="org.liying";
    private final long EXPIRATION_TIME =86400*1000;

    public String generateToken(User user){
        // JWT signature Algorithm

        // part1：header
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // SECRET_KEY
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // part 2：Claims(Payload)
        Claims claims = Jwts.claims();
        claims.setId(String.valueOf(user.getId()));
        claims.setSubject(user.getName());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));

        // 向payload里面加上permission信息
        Set<Role> roles = user.getRoles123();
        String allowedReadResources = "";
        String allowedCreateResources = "";
        String allowedUpdateResources = "";
        String allowedDeleteResources = "";

        // for each role in roles
        // 从role里面取出permission
        for (Role role : roles) {
            if (role.isAllowedRead())
                allowedReadResources = String.join(role.getAllowedResource(), allowedReadResources, ",");
            if (role.isAllowedCreate())
                allowedCreateResources = String.join(role.getAllowedResource(), allowedCreateResources, ",");
            if (role.isAllowedUpdate())
                allowedUpdateResources = String.join(role.getAllowedResource(), allowedUpdateResources, ",");
            if (role.isAllowedDelete())
                allowedDeleteResources = String.join(role.getAllowedResource(), allowedDeleteResources, ",");
        }
        // 以key-value pair的形式放到claim里面去
        // 把最后一个，去掉（,$ match the last",",  then replace "," with "".）
        claims.put("allowedReadResources", allowedReadResources.replaceAll(",$", ""));
        claims.put("allowedCreateResources", allowedCreateResources.replaceAll(",$", ""));
        claims.put("allowedUpdateResources", allowedUpdateResources.replaceAll(",$", ""));
        claims.put("allowedDeleteResources", allowedDeleteResources.replaceAll(",$", ""));

//        String allowedResource = roles.stream().map(role -> role.getAllowedResource()).collect(Collectors.joining(","));
//        claims.put("allowedResource", allowedResource);

        // Builds the JWT and serialize it to a compact, URL-safe string      part1+part2+key -> token
        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }
    public Claims decryptJwtToken(String token) {
        // byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
        logger.debug("Claims: " + claims.toString());
        return claims;
    }
}
