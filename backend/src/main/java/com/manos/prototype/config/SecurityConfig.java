package com.manos.prototype.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
    @Autowired
    private UserDetailsService userDetailsService;
    
//    @Autowired
//    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
   
   	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf()//.disable()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) 
				.and()
			.authorizeRequests()
				.antMatchers("login").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.usernameParameter("email")
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));
//				.deleteCookies("JSESSIONID")
//				.invalidateHttpSession(true);
	//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
}
