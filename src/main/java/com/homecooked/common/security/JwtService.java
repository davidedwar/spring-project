package com.homecooked.common.security;

import com.homecooked.common.constant.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    String jwtSecretKey;

    @Value("${application.security.jwt.expiration}")
    Long tokenExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    long refreshExpiration;

    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Role extractRole(String token) {
        final Claims claims = extractAllClaims(token);
        if (!claims.isEmpty())
            return Role.valueOf(claims.get("role", String.class));
        else
            return null;
    }

    public JwtResponse generateToken(UserDetails userDetails, Role role) {
        if (role == null)
            return generateToken(new HashMap<>(), userDetails);
        else {
            HashMap<String, Object> roleMap = new HashMap<>();
            roleMap.put("role", role.name());
            return generateToken(roleMap, userDetails);
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && isTokenNotExpired(token);
    }

    public boolean isRefreshTokenValid(String refreshToken, UserDetails userDetails) {
        final String userName = extractUserName(refreshToken);
        return (userName.equals(userDetails.getUsername())) && isTokenNotExpired(refreshToken);
    }

    public JwtResponse refreshAccessToken(JwtResponse jwt) throws Exception {
        try {
            String userEmail = extractUserName(jwt.getRefreshToken());
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
            if (isRefreshTokenValid(jwt.getRefreshToken(), userDetails))
                return generateToken(new HashMap<>(), userDetails);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        throw new AccessDeniedException("Access Denied");
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private JwtResponse generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        Date now = new Date(System.currentTimeMillis());
        Date accessExpiration = new Date(System.currentTimeMillis() + tokenExpiration);
        Date refreshExpiration = new Date(System.currentTimeMillis() + this.refreshExpiration);

        String accessToken = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(accessExpiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(refreshExpiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return JwtResponse.builder()
                .accessToken(accessToken)
                .expires(accessExpiration.toString())
                .refreshToken(refreshToken)
                .build();
    }

    private boolean isTokenNotExpired(String token) {
        return extractExpiration(token).after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
