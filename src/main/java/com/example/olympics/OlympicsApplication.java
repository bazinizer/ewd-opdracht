package com.example.olympics;

import java.util.List;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import domain.Sport;

import service.MyUserService;
import service.SportService;
import service.StadiumService;
import service.SportServiceImpl;
import service.StadiumServiceImpl;
import service.WedstrijdService;
import service.WedstrijdServiceImpl;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class OlympicsApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(OlympicsApplication.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/sport");
        registry.addViewController("/wedstrijden").setViewName("wedstrijden");
        registry.addViewController("/403").setViewName("403");
        
        
    }

    @Bean
    public SportService sportService() {
        return new SportServiceImpl();
    }
    @Bean
	public MyUserService myUserService() {
		return new MyUserService();
	}
    @Bean
    public WedstrijdService wedstrijdService() {
    	return new WedstrijdServiceImpl();
    }
    @Bean
    public StadiumService StadiumService() {
    	return new StadiumServiceImpl();
    }
    
}
