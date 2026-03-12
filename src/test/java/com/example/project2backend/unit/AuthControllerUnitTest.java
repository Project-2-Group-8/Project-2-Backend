package com.example.project2backend.unit;

import com.example.project2backend.auth.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthControllerUnitTest {

    private final AuthController controller = new AuthController();

    @Test
    void loginRedirectsToGoogle() {
        ResponseEntity<Void> response = controller.login();
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("/oauth2/authorization/google", response.getHeaders().getLocation().toString());
    }

    @Test
    void signupRedirectsToGoogle() {
        ResponseEntity<Void> response = controller.signup();
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("/oauth2/authorization/google", response.getHeaders().getLocation().toString());
    }

    @Test
    void meWithoutUserReturnsUnauthenticated() {
        Map<String, Object> response = controller.me(null);
        assertEquals(false, response.get("authenticated"));
    }

    @Test
    void meWithUserReturnsAuthenticated() {
        OAuth2User mockUser = mock(OAuth2User.class);
        when(mockUser.getAttribute("name")).thenReturn("Alice");
        when(mockUser.getAttribute("email")).thenReturn("alice@example.com");
        when(mockUser.getAttribute("picture")).thenReturn("pic.jpg");

        Map<String, Object> response = controller.me(mockUser);

        assertEquals(true, response.get("authenticated"));
        assertEquals("Alice", response.get("name"));
        assertEquals("alice@example.com", response.get("email"));
        assertEquals("pic.jpg", response.get("picture"));
    }

    @Test
    void logoutReturnsNoContent() {
        ResponseEntity<Void> response = controller.logout();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}