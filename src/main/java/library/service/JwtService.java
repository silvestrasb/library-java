package library.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import library.dto.UserDTO;
import library.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private byte[] secret;

    @Value("${security.jwt.validity-min}")
    private long validityMin;

    private final ObjectMapper objectMapper;

    public JwtService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String createToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("book-api")
                .setAudience("book-ui")
                .setSubject(user.getUsername())
//                .setSubject(user.getEmail()) // CHANGED
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityMin * 60000))
                .claim("user", new UserDTO(user))
                .signWith(Keys.hmacShaKeyFor(secret), SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication parseToken(String jwt) throws JsonProcessingException {
        Claims parsedJwtBody;

        try {
            parsedJwtBody = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return null;
        }

        UserDTO userDTO = objectMapper.readValue(objectMapper.writeValueAsString(parsedJwtBody.get("user")), UserDTO.class);
        User user = new User(userDTO);

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
