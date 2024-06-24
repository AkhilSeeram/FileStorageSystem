package com.vcriate.demo;

import com.vcriate.demo.models.User;
import com.vcriate.demo.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode("6440945DF5C4E300C3DC75C26E162E0AD0B1398796D9693BC9F97E323E482B5059F90BF64A0C6DB7B02D81E242EAF3F2BFE6D633B5E51A9C9689DB88A10BFA65");
        return Keys.hmacShaKeyFor(decodedKey);
    }

    @Test
    public void testGenerateToken() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        String token = jwtService.generateToken(user);

        assertNotNull(token);

        Claims claims= Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        assertEquals("test@example.com", claims.getSubject());
        assertEquals("1", claims.get("id"));
    }

    @Test
    public void testExtractUsername() {
        User user = new User();
        user.setEmail("test@example.com");

        String token = jwtService.generateToken(user);

        String username = jwtService.extractUsername(token);

        assertEquals("test@example.com", username);
    }
}
