package br.com.erp_api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erp_api.controller.dto.UserDto;
import br.com.erp_api.controller.form.UserForm;
import br.com.erp_api.model.user.User;
import br.com.erp_api.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	   
	   @Autowired
	   private PasswordEncoder passwordEncoder;
		
		@Autowired
		private UserRepository userRepository;

		@PostMapping
		public ResponseEntity<Object> Cadastrar(@RequestBody UserForm form){

			Optional<User> usuario = userRepository.findByEmail(form.getEmail());	
			if (usuario.isPresent()) {
				return ResponseEntity.badRequest()
			            .body("Email já cadastrado");
			}		

			
			
		    form.setSenha(passwordEncoder.encode(form.getSenha()));
			User user = new User(form);
		    

		    userRepository.save(user);
			return ResponseEntity.ok(new UserDto(user)); 
       }
		
		@DeleteMapping("/{id}")
		public ResponseEntity<Object> Deletar(@PathVariable Long id) {
			Optional<User> usuario = userRepository.findById(id);	
			if (usuario.isEmpty()) {
				return ResponseEntity.badRequest().body("Não foi encontrado este estabelecimento");
			}
			userRepository.delete(usuario.get());

			return ResponseEntity.noContent().build();
		}		
}
