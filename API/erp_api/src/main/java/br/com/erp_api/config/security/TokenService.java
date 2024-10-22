package br.com.erp_api.config.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.erp_api.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

	@Value("${g.erp.jwt.expiration}") // pega do properties
	private String expiration;

	@Value("${g.erp.jwt.secret}") // pega do properties
	private String secret; // senha da criptografia

	public String geraToken(Authentication authenticate) {
		User logado = (User) authenticate.getPrincipal();
		Date hoje = new Date();

		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

		return Jwts.builder().setSubject(logado.getEmail()).setIssuedAt(hoje).setExpiration(dataExpiracao)
				.signWith(getSigninKey(), SignatureAlgorithm.HS512).compact();
	}

	public Key getSigninKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		return key;
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody().getSubject();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Long getIdUser(String token) {
		Claims claims = Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());

	}
}