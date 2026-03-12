package com.example.project2backend.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  // "Login page" endpoint:
  // Supabase handles OAuth login on the frontend.
  @GetMapping("/auth/login")
  public ResponseEntity<Void> login() {
    return ResponseEntity.noContent().build();
  }

  // "Signup page" endpoint:
  // No backend redirect required.
  @GetMapping("/auth/signup")
  public ResponseEntity<Void> signup() {
    return ResponseEntity.noContent().build();
  }

  // Returns information about the currently authenticated user.
  // Call with:
  // Authorization: Bearer <supabase_access_token>
  @GetMapping("/auth/me")
  public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
    Map<String, Object> response = new LinkedHashMap<>();
    if (jwt == null) {
      response.put("authenticated", false);
      return response;
    }

    response.put("authenticated", true);

    // Supabase JWT standard claims
    response.put("sub", jwt.getSubject());
    response.put("email", jwt.getClaimAsString("email"));
    response.put("role", jwt.getClaimAsString("role"));

    return response;
  }

  // Logout:
  @PostMapping("/auth/logout")
  public ResponseEntity<Void> logout() {
    return ResponseEntity.noContent().build();
  }
}