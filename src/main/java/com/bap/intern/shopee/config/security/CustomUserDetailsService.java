package com.bap.intern.shopee.config.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bap.intern.shopee.entity.User;
import com.bap.intern.shopee.entity.User.Role;
import com.bap.intern.shopee.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Build UserDetails bằng CustomUserDetails đã thiết lập
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);	// Lấy user theo email

        if (userOptional.isEmpty()) {
            System.out.println("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        User user = userOptional.get();
        System.out.println("Found User: " + user);

        // Lấy role của user
        Set<Role> roles = user.getRoles();

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roles != null) {
        	grantList = roles.stream()
            					.map(role -> new SimpleGrantedAuthority(role.getName()))
            					.collect(Collectors.toList());
        }
        return new CustomUserDetails(user.getEmail(), user.getPassword(), user.getId(), grantList);
    }

}
