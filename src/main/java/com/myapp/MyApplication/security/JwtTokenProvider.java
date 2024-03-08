package com.myapp.MyApplication.security;

import com.myapp.MyApplication.exceptions.BlogApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String jwt_secret_key;
    @Value("${app.jwt.expiration-milliseconds}")
    private long jwt_expiration_time;

    public String generateToken(Authentication  authentication){
        String username = authentication.getName();
        Date currDate = new Date();
        Date expDate = new Date(currDate.getTime()+jwt_expiration_time);
        return Jwts.builder()
                .subject(username)
                .issuedAt(currDate)
                .expiration(expDate)
                .signWith(key())
                .compact();
    }

    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwt_secret_key));
    }

    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }

    public Boolean validateToken(String token){
        try{
            Jwts.parser().
                    verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        }
        catch(MalformedJwtException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid JWT token");
        }
        catch(ExpiredJwtException e){
            throw  new BlogApiException(HttpStatus.UNAUTHORIZED,"Token Expired");
        }
        catch (UnsupportedJwtException e){
            throw new BlogApiException(HttpStatus.UNAUTHORIZED,"UnSupported JWT Token");
        }
        catch (IllegalArgumentException e){
            throw new BlogApiException(HttpStatus.UNAUTHORIZED,"Token is Null or Empty");
        }
    }


}
