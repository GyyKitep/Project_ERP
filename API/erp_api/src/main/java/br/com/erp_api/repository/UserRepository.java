package br.com.erp_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erp_api.model.user.User;


public interface UserRepository extends JpaRepository<User, Long>{ 
	
   Optional<User> findByEmail(String email);

}
