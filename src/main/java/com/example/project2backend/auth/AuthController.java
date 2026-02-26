package com.example.project2backend.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  // "Login page" endpoint: frontend navigates here.
  @GetMapping("/auth/login")
  public ResponseEntity<Void> login() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/oauth2/authorization/google");
    return new ResponseEntity<>(headers, HttpStatus.FOUND);
  }

  // "Signup page" endpoint: OAuth2 signup = first login creates account in DB later.
  @GetMapping("/auth/signup")
  public ResponseEntity<Void> signup() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/oauth2/authorization/google");
    return new ResponseEntity<>(headers, HttpStatus.FOUND);
  }

  @GetMapping("/auth/me")
  public Map<String, Object> me(@AuthenticationPrincipal OAuth2User user) {
    Map<String, Object> response = new LinkedHashMap<>();
    if (user == null) {
      response.put("authenticated", false);
      return response;
    }
    response.put("authenticated", true);
    response.put("name", user.getAttribute("name"));
    response.put("email", user.getAttribute("email"));
    response.put("picture", user.getAttribute("picture"));
    return response;
  }

  // Logout (Spring Security intercepts this path)
  @PostMapping("/auth/logout")
  public ResponseEntity<Void> logout() {
    return ResponseEntity.noContent().build();
  }
}