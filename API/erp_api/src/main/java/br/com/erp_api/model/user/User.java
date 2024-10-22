package br.com.erp_api.model.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.erp_api.controller.form.UserForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ERP_USER")
@Getter
@Setter
public class User implements UserDetails{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true) 
	private String email;
	
	private String senha;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(UserForm form) {
		this.email = form.getEmail();
		this.senha = form.getSenha();
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	@Override
	public String getPassword() {
		return senha;
	}
	@Override
	public String getUsername() {
		return email;
	}

}
