package com.manos.prototype.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
   
   	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf()
//				.disable()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) 
				.and()
			.authorizeRequests()
				.antMatchers("login").permitAll()
				.antMatchers("/users/new-password").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.failureHandler(authenticationFailureHandler())
				.usernameParameter("email")
				.and()
			.rememberMe()
				.userDetailsService(userDetailsService)
				.and()
			.exceptionHandling().authenticationEntryPoint(unauthenticatedRequestHandler())
				.and()
			.logout()
				.logoutSuccessHandler(logoutSuccessHandler());
   	}
   	
   	@Bean
   	public LogoutSuccessHandler logoutSuccessHandler() {
   		return new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
					throws IOException, ServletException {
			}
		};
   	}
   	
   	@Bean
   	public AuthenticationFailureHandler authenticationFailureHandler() {
   		return new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
//				throw new EntityNotFoundException(User.class);
			}
		};
   	}
   	
   	//beans
 	//bcrypt bean definition
 	@Bean
 	public BCryptPasswordEncoder passwordEncoder() {
 		return new BCryptPasswordEncoder();
 	}

 	@Bean
 	public DaoAuthenticationProvider authenticationProvider() {
 		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
 		auth.setUserDetailsService(userDetailsService); //set the custom user details service
 		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
 		return auth;
 	}
 	
 	@Bean
 	UnauthenticatedRequestHandler unauthenticatedRequestHandler() {
 	    return new UnauthenticatedRequestHandler();
 	}

 	static class UnauthenticatedRequestHandler implements AuthenticationEntryPoint {
 	    @Override
 	    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
 	    }
 	}
}
