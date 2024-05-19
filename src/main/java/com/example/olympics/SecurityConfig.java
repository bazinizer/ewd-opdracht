package com.example.olympics;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import service.MyUserService;
@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
		@Autowired
		DataSource dataSource;
	
	
    	@Autowired
    	private MyUserService myUserService;


	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

	        auth.userDetailsService(myUserService).passwordEncoder(new BCryptPasswordEncoder());
	    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository()))
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/login**").permitAll()
                        		.requestMatchers("/css/**").permitAll()
                                .requestMatchers("/403**").permitAll()
                                .requestMatchers("/sport/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/wedstrijden/{id}/create/**").hasRole("ADMIN")
                                .requestMatchers("/wedstrijden/{id}/koopTicket/**").hasRole("USER")
                                .requestMatchers("/wedstrijden/**").hasAnyRole("USER", "ADMIN"))
                				
                				
                
                .formLogin(form ->
                        form.defaultSuccessUrl("/sport", true)
                                .loginPage("/login")
                                .usernameParameter("username").passwordParameter("password")
                )
                .exceptionHandling(handling -> handling.accessDeniedPage("/403"));

        return http.build();
    	        
    }
}

