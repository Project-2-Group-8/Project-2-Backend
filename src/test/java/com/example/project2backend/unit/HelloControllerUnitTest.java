package com.example.project2backend.unit;

import com.example.project2backend.api.HelloController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelloControllerUnitTest {

    @Mock
    OAuth2User user;

    @InjectMocks
    HelloController controller;

    @Test
    void helloReturnsExpectedMessage() {
        when(user.getAttribute("email")).thenReturn("user@example.com");

        Map<String, Object> result = controller.hello(user);

        assertEquals("Authenticated ✅", result.get("message"));
        assertEquals("user@example.com", result.get("email"));
    }

    @Test
    void testMethodReturnsVoid() {
        controller.test();
    }
}