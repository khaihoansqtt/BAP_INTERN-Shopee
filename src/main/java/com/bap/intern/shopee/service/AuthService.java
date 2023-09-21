package com.bap.intern.shopee.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bap.intern.shopee.dto.auth.LoginRes;
import com.bap.intern.shopee.dto.auth.RegisterReq;
import com.bap.intern.shopee.dto.auth.RegisterRes;
import com.bap.intern.shopee.entity.User;
import com.bap.intern.shopee.entity.User.Role;
import com.bap.intern.shopee.exception.customException.ExistedEmailException;
import com.bap.intern.shopee.repository.UserRepository;
import com.bap.intern.shopee.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder bcryptEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
	
	public RegisterRes register(RegisterReq req) {
		String email = req.getEmail();
		Optional<User> userOptional = userRepository.findByEmail(email);
		
		if (userOptional.isEmpty()) {
			int[] roleOrdinals = req.getRoleOrdinals();
			Set<Role> roles = Arrays.stream(roleOrdinals)
								        .mapToObj(Role::of)
								        .collect(Collectors.toSet());
//			roles = Arrays.stream(req.getRoleOrdinal()).map(r -> Role.of(r)).collect(Collectors.toSet());
			User newUser = User.builder().email(req.getEmail())
										.password(bcryptEncoder.encode(req.getPassword()))
										.name(req.getName())
										.roles(roles)
										.build();
			userRepository.save(newUser);
			log.info("Register user successfully with email: " + email);
			return new RegisterRes(newUser);
		} else throw new ExistedEmailException();
	}
	
	public LoginRes login(String email, String password) {
		// Xác thực bằng email và mật khẩu
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        log.info("Login successfully");
        
        return new LoginRes(accessToken, refreshToken);
	}
}
