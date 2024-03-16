//package config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//		authenticationManagerBuilder.inMemoryAuthentication()
//		.passwordEncoder(NoOpPasswordEncoder.getInstance())
//		.withUser("eureka").password("password")
//		.authorities("USER");
//	}
//	
//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		 http
//         .authorizeHttpRequests((authz) -> authz
//             .anyRequest().authenticated()
//         )
//         .httpBasic();
//        return http.build();
//    }
//
//}
