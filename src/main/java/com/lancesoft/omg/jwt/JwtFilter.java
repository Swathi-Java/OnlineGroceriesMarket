package com.lancesoft.omg.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lancesoft.omg.service.SecurityUserDetailsService;
@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	SecurityUserDetailsService securityUserDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	Logger logger = Logger.getLogger(JwtFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest httpserveltrequest, HttpServletResponse httpservletresponse, FilterChain filterChain)
			throws ServletException, IOException {
		 logger.info("Inside doFilterInternal method");
		 String authorizationHeader=httpserveltrequest.getHeader("Authorization");
		 
		 
		 String token=null;
		 String userName=null;
		 if(authorizationHeader!=null&&authorizationHeader.startsWith("Bearer"))
		 {
			 token=authorizationHeader.substring(7);
			 userName=jwtUtil.extractUsername(token);
		 }
		 if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		 {
			 UserDetails userDetails=securityUserDetailsService.loadUserByUsername(userName);
			 if(jwtUtil.validateToken(token, userDetails))
			 {
				 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpserveltrequest));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			 }
		 }
		 filterChain.doFilter(httpserveltrequest, httpservletresponse);
		 System.out.println(token+"In Do filter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

}
