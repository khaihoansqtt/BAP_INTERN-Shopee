package com.bap.intern.shopee.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bap.intern.shopee.dto.user.PatchUserReq;
import com.bap.intern.shopee.dto.user.PatchUserRes;
import com.bap.intern.shopee.entity.User;
import com.bap.intern.shopee.entity.User.Role;
import com.bap.intern.shopee.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	// userId lấy từ authentication rồi nên không cần check xem user có tồn tại không
	public PatchUserRes patchUser(int userId, PatchUserReq req) {
		User user = userRepository.findById(userId).get();
		if (req.getEmail() != null) user.setEmail(req.getEmail());
		if (req.getName() != null) user.setName(req.getName());
		if (req.getRoleOrdinals() != null) user.setRoles(Arrays.stream(req.getRoleOrdinals())
																	.mapToObj(Role::of)
																	.collect(Collectors.toSet()));
		userRepository.save(user);
		log.info("update user successfully");
		return new PatchUserRes(user);
	}

	public void deleteUser(int userId) {
		userRepository.deleteById(userId);
		log.info("delete user successfully, id = " + userId);
	}
}
