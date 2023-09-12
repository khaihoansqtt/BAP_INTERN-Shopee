package com.bap.intern.shopee.util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.bap.intern.shopee.config.security.CustomUserDetails;
import com.bap.intern.shopee.exception.customException.UnauthenticatedException;

@Service
public class AuthUtil {
	// Lấy userId của phiên đăng nhập
	public int getUserId(Authentication authentication) {
		if (authentication != null) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			return userDetails.getId();
		} else {
			throw new UnauthenticatedException("You need to login");
		}
	}
}
