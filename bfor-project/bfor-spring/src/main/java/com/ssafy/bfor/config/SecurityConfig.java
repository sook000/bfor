package com.ssafy.bfor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.anyRequest().permitAll();
		return http.build();
	}

//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//
//		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
//		configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
//		configuration.setAllowedHeaders(Arrays.asList("*"));
//		configuration.setAllowCredentials(true);
//		
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
//	@Bean
//	    public WebMvcConfigurer corsConfigurer() {
//	        return new WebMvcConfigurer() {
//	            @Override
//	            public void addCorsMappings(CorsRegistry registry) {
//	                registry.addMapping("/**")
//	                    .allowedOrigins("http://localhost:5173")
//	                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//	                    .allowedHeaders("*")
//	                    .allowCredentials(true);
//	            }
//	        };
//	    }
	
//	 @Bean
//	   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
//	        http.cors().and().csrf().disable()
//	         .and().authorizeRequests() 
//	            .antMatchers("/**").permitAll()
//	            .antMatchers("/**").authenticated()
//	            .anyRequest().authenticated()
//	      return http.build();
//	   }
}
