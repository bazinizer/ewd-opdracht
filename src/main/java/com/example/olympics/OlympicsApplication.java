package com.example.olympics;

import java.util.Locale;

import org.springframework.boot.SpringApplication;



import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import perform.PerformRestExample;
import service.MyUserService;
import service.SportService;
import service.SportServiceImpl;
import service.StadiumService;
import service.StadiumServiceImpl;
import service.TicketService;
import service.TicketServiceImpl;
import service.WedstrijdService;
import service.WedstrijdServiceImpl;
import validator.DateTimeValidator;
import validator.OlympicNumber1Validator;
import validator.OlympicNumber2Validator;
import validator.TicketPurchaseValidator;



@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class OlympicsApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(OlympicsApplication.class, args);
        
        try {
        	new PerformRestExample();
        	System.out.println("HAAAALLLLOOO");
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/sport");
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
    public StadiumService stadiumService() {
    	return new StadiumServiceImpl();
    }
    @Bean
    public TicketService ticketService() {
    	return new TicketServiceImpl();
    }
    

    @Bean
    public DateTimeValidator dateTimeValidation() {
    	return new DateTimeValidator();
    }
    @Bean
    public OlympicNumber1Validator olympicNumber1Validator() {
    	return new OlympicNumber1Validator();
    }
    @Bean
    public OlympicNumber2Validator olympicNumber2Validator() {
    	return new OlympicNumber2Validator();
    }
    
    @Bean
    public TicketPurchaseValidator purchaseValidator() {
    	return new TicketPurchaseValidator();
    }
    
    @Bean
    public WedstrijdRestController wedstrijdRestController() {
    	return new WedstrijdRestController();
    }
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.forLanguageTag("nl"));
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
    
}
