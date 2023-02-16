package com.lancesoft.omg.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import com.lancesoft.omg.jwt.JwtFilter;
@Configuration
@EnableWebSecurity(debug = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService secureuserDetailsService;
	@Autowired
	JwtFilter jwtfilter;
	
	private static Logger logger = Logger.getLogger(MySecurityConfig.class);

	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		logger.info("Inside authenticationManagerBuilder");
		auth.userDetailsService(secureuserDetailsService).passwordEncoder(getPassWordEncoder());
	}
	protected void configure(HttpSecurity httpsecurity) throws Exception {
		httpsecurity.cors();
		httpsecurity.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers","Authorization"));
		
		httpsecurity.csrf().disable();
		logger.info("Inside HttpSecurity");
		httpsecurity.authorizeRequests().antMatchers("/api/login","/api/sendOtp","/api/register","/api/userprofile","/api/admin/sendotp","/api/admin/register","/api/user/mycart","api/user/getMycartList","/api/user/updatemycart","api/user/deletemycartlist","api/user/deleteCartITem","api/user/saveaddress").permitAll().anyRequest().authenticated().and().exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpsecurity.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
	
	}
	@Bean
	public PasswordEncoder getPassWordEncoder() {
		
		 return new BCryptPasswordEncoder();
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}
}