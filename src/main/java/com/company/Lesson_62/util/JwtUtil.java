package com.company.Lesson_62.util;

import com.company.Lesson_62.dto.ProfileJwtDTO;
import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.exeptions.ForbiddenException;
import com.company.Lesson_62.exeptions.UnauthorizedException;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {

    private final static String secretKey = "kalitso'z";

    public static String createJwt(Integer id) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)));
        jwtBuilder.setIssuer("kun.uz");
        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static String createJwt(Integer id, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)));
        jwtBuilder.setIssuer("kun.uz");
        jwtBuilder.claim("role", role.name());

        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static ProfileJwtDTO decodeJwt(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = (Claims) jws.getBody();
        String id = claims.getSubject();
        ProfileRole role = ProfileRole.valueOf((String) claims.get("role"));

        return new ProfileJwtDTO(Integer.parseInt(id), role);
    }

    public static Integer decodeJwtAndGetId(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = (Claims) jws.getBody();
        return Integer.valueOf(claims.getSubject());
    }

    public static Integer getCurrentUser(HttpServletRequest request) throws RuntimeException {

        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("METHOD NOT ALLOWED");
        }
        return userId;
    }

    public static ProfileJwtDTO getProfile(HttpServletRequest request,ProfileRole requiredRole){

        ProfileJwtDTO jwtDTO = (ProfileJwtDTO) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new UnauthorizedException("Not authorized");
        }
        if (!jwtDTO.getRole().equals(requiredRole)){
            throw new ForbiddenException("Forbidden exp");
        }
        return jwtDTO;
    }


    public static ProfileJwtDTO getProfile(HttpServletRequest request){

        ProfileJwtDTO jwtDTO = (ProfileJwtDTO) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new RuntimeException("METHOD NOT ALLOWED");
        }
        return jwtDTO;
    }

}
