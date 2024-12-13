package com.spring.jwt.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.jwt.Helper.JwtUtil;
import com.spring.jwt.services.CustomUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private CustomUserDetailService customuserdetail;
	
	@Autowired
	private JwtUtil jwtutil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
		String requestTokenHeader = request.getHeader("Authorization");
		
		String username=null;
		String jwtToken=null;
		
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Berer ")) {
			
			 jwtToken = requestTokenHeader.substring(6);
		
			 System.out.println("jwtauth"+jwtToken);	
			try {
				
				 username = this.jwtutil.extractUsername(jwtToken);
				System.out.println("jwtauthuswer"+username);
				 
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			UserDetails userDetails = this.customuserdetail.loadUserByUsername(username);
			
			System.out.println("authuserdetail"+userDetails);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			
			}
		
		}
		
		filterChain.doFilter(request, response);
	
	}
	
	
}
