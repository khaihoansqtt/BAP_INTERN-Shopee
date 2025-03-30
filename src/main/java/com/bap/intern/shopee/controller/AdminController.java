package com.bap.intern.shopee.controller;

import com.bap.intern.shopee.dto.admin.AdminSendEmailReq;
import com.bap.intern.shopee.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/systems")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	
	@PostMapping("/send-email-to-user")
	public ResponseEntity<?> sendEmailToUser(@RequestBody AdminSendEmailReq req) throws JobExecutionException {
		return ResponseEntity.ok(adminService.sendEmailToUser(req));
	}
}
