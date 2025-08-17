package com.ycw.personal_blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public UserDetailsService users(PasswordEncoder encoder) {
		return new InMemoryUserDetailsManager(
				User.builder()
						.username("admin")
						.password(encoder.encode("password"))
						.roles("ADMIN")
						.build());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain web(HttpSecurity http) throws Exception {
		http
				.formLogin(form -> form
						.loginPage("/login")
						.permitAll())
				.logout(logout -> logout.permitAll())
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/admin").hasRole("ADMIN")
						.requestMatchers("/edit/**").hasRole("ADMIN")
						.requestMatchers("/new").hasRole("ADMIN")
						// .anyRequest().authenticated());
						.anyRequest().permitAll());

		return http.build();
	}
}
