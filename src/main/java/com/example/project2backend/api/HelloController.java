package com.example.project2backend.api;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

  @GetMapping("/hello")
  public Map<String, Object> hello(@AuthenticationPrincipal OAuth2User user) {
    return Map.of(
        "message", "Authenticated ✅",
        "email", user.getAttribute("email")
    );
  }
}