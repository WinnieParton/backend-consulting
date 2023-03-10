
package esgi.infra.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import esgi.infra.service.impl.UserDetailsImpl;
import io.jsonwebtoken.*;
import java.util.Date;

@Component
public class JwtUtils {
	private static final Logger loggerErr = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${monwin.app.jwtSecret}")
	private String jwtSecret;

	@Value("${monwin.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			loggerErr.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			loggerErr.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			loggerErr.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			loggerErr.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			loggerErr.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}