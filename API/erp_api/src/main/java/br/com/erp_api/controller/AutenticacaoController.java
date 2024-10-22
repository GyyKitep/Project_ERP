package br.com.erp_api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erp_api.config.security.TokenService;
import br.com.erp_api.controller.dto.TokenDto;
import br.com.erp_api.controller.form.LoginForm;
import br.com.erp_api.controller.form.UserForm;
import br.com.erp_api.model.user.User;
import br.com.erp_api.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	

	   @Autowired
	   private AuthenticationManager authManager;
		
		@Autowired
		private TokenService tokenService;
		
		@PostMapping
		public ResponseEntity<TokenDto> autenticar(@RequestBody LoginForm form){
			UsernamePasswordAuthenticationToken dadosLogin = form.converter();
			
			try {
				Authentication authenticate = authManager.authenticate(dadosLogin);
				String token = tokenService.geraToken(authenticate);
				return ResponseEntity.ok(new TokenDto(token, "Bearer"));
				
			} catch (BadCredentialsException e) {
				return ResponseEntity.badRequest().build();
			}
			
		}				

}
