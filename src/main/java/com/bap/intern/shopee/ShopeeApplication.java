package com.bap.intern.shopee;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bap.intern.shopee.entity.User;
import com.bap.intern.shopee.entity.User.Role;
import com.bap.intern.shopee.repository.UserRepository;

@SpringBootApplication
@EnableCaching
public class ShopeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopeeApplication.class, args);
	}
	
	// Tạo admin nếu trong db chưa có admin
	@Bean
	public CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return (String... args) -> {
			if (userRepository.findById(1).isEmpty()) {
				Set<Role> roles = new HashSet<>();
				roles.add(Role.ADMIN);
				User newUser = new User(1, "admin@email", passwordEncoder.encode("12345678"), "admin", roles, null);
				userRepository.save(newUser);
			}
		};
	}
}
