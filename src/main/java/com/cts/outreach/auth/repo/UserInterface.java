package com.cts.outreach.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cts.outreach.auth.entity.UserEntity;

public interface UserInterface extends JpaRepository<UserEntity, Long>{
	
	@Query("select u from UserEntity u where u.username = :username")
	public UserEntity getUserByName(@Param("username") String username);
	
	@Query("select u from UserEntity u where u.email = :email")
	public UserEntity getUserByEmail(@Param("email") String email);
	
}
