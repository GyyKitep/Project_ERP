package br.com.erp_api.config.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.erp_api.model.user.User;
import br.com.erp_api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AutenticacaoViaTokenFilter  extends OncePerRequestFilter{

	private TokenService tokenService;
	private UserRepository userRepository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UserRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.userRepository = usuarioRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		boolean valido = tokenService.isTokenValido(token);
		if (valido) {
			autenticarCliente(token);
		}
		
		
		filterChain.doFilter(request, response);
		
	}

	private void autenticarCliente(String token) {
		Long idUsuario = tokenService.getIdUser(token);
		User usuario = userRepository.findById(idUsuario).get();		
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);		
	}

	private String recuperarToken(HttpServletRequest request) {		
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}	
		
		return token.substring(7, token.length());
	}

}
