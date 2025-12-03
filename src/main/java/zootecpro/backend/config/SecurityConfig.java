package zootecpro.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import zootecpro.backend.services.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Configuration
  @Order(1)
  public static class AdminSecurityConfig {

    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
      http
          .securityMatcher("/admin/**")
          .authorizeHttpRequests(authorize -> authorize
              .requestMatchers("/admin/login", "/admin/css/**").permitAll()
              .requestMatchers("/admin/**")
              .hasRole("SUPERADMIN"))
          .formLogin(form -> form
              .loginPage("/admin/login")
              .loginProcessingUrl("/admin/login")
              .defaultSuccessUrl("/admin/users")
              .failureUrl("/admin/login?error=true")
              .permitAll())
          .logout(logout -> logout
              .logoutUrl("/admin/logout")
              .logoutSuccessUrl("/admin/login?logout=true")
              .permitAll())
          .sessionManagement(session -> session
              .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

      return http.build();
    }
  }

  @Configuration
  @Order(2)
  public static class ApiSecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public ApiSecurityConfig(JwtAuthenticationFilter jwtFilter) {
      this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
      http
          .securityMatcher("/api/**")
          .authorizeHttpRequests(authorize -> authorize
              .requestMatchers("/api/login", "/api/register").permitAll()
              .anyRequest().authenticated())
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
          .sessionManagement(session -> session
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .csrf(AbstractHttpConfigurer::disable);

      return http.build();
    }
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
