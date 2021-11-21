package com.example.demo.security.jwt;

import com.example.demo.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret="taipt";
    private int jwtExpiration=86400;


    //Tạo token
    public String createToke(Authentication authentication){
        UserPrinciple userPrinciple=(UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setExpiration(new Date(new Date().getTime()+jwtExpiration*10000))
                .setIssuedAt(new Date())// set thời điểm hiện tại
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    // Ham validate token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch(SignatureException ex){
            logger.error(" Invalid Signature Jwt: "+ ex);
        }catch (MalformedJwtException ex){
            logger.error(" Invalid format Jwt token: "+ ex);
        }catch (ExpiredJwtException ex ){
            logger.error(" Expired Jwt token : "+ ex);
        }catch (UnsupportedJwtException ex){
            logger.error("Unsupport Jwt token : "+ ex);
        }catch (IllegalArgumentException ex ){
            logger.error("Jwt claims is empty: "+ ex);
        }
        return false;
    }

    // Ham get userName from token
    public String getUserNameFromToken( String token){
        String userName= Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userName;
    }

}
