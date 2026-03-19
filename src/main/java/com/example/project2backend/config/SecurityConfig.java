package com.example.project2backend.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Value("${app.frontend.base-url}")
  private String frontendBaseUrl;

  @Value("${supabase.jwk-set-uri}")
  private String jwkSetUri;

  @Value("${supabase.issuer}")
  private String issuer;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            .requestMatchers("/").permitAll()
            .requestMatchers("/health").permitAll()

            // Auth check endpoint should require a token
            .requestMatchers("/auth/me").authenticated()
            .requestMatchers("/api/admin/**").hasRole("ADMIN")

            .requestMatchers("/api/**").permitAll()
                               
            .anyRequest().permitAll()
        )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return http.build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

    OAuth2TokenValidator<Jwt> defaultValidators = JwtValidators.createDefault();
    OAuth2TokenValidator<Jwt> issuerValidator = new JwtIssuerValidator(issuer);

    decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(defaultValidators, issuerValidator));
    return decoder;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowedOrigins(List.of(frontendBaseUrl));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
    config.setAllowCredentials(false);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
    return jwtAuthenticationConverter;
  }

  public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
    JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();

    return jwt -> {
      List<GrantedAuthority> authorities = new ArrayList<>(scopesConverter.convert(jwt));

      String topLevelRole = jwt.getClaimAsString("role");
      if (topLevelRole != null && !topLevelRole.isBlank()) {
        authorities.add(new SimpleGrantedAuthority("ROLE_" + topLevelRole.toUpperCase()));
      }

      Map<String, Object> appMetadata = jwt.getClaimAsMap("app_metadata");
      if (appMetadata != null) {
        Object appRole = appMetadata.get("role");
        if (appRole instanceof String role && !role.isBlank()) {
          authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
        }

        Object appRoles = appMetadata.get("roles");
        if (appRoles instanceof Collection<?> roles) {
          for (Object roleValue : roles) {
            if (roleValue instanceof String role && !role.isBlank()) {
              authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
            }
          }
        }
      }

      return authorities;
    };
  }
}
