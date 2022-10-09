package com.employees.service.config;

import com.employees.service.model.EmployeeRole;
import com.employees.service.service.EmployeeDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider implements Serializable {

	private static final long serialVersionUID = 2569800841756370596L;

	private final static long VALIDITY_TIME_IN_MILLISECONDS  = 5 * 60000; // 5 minutes

	final private EmployeeDetailsService employeeDetailsService;

	public JwtTokenProvider(EmployeeDetailsService employeeDetailsService) {
		this.employeeDetailsService = employeeDetailsService;
	}

	@Value("${jwt.secret-key}")
	private String secretKey;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String employeeEmail, EmployeeRole employeeRole) {
		Claims claims = Jwts.claims().setSubject(employeeEmail);
		claims.put("auth", employeeRole);

		Date now = new Date();
		return Jwts.builder().setClaims(claims).setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + VALIDITY_TIME_IN_MILLISECONDS))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public Authentication getAuthentication(String employeeEmail) {
		UserDetails userDetails = employeeDetailsService.loadUserByUsername(employeeEmail);
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	}

	public Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

}