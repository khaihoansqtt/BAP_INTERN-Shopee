package com.bap.intern.shopee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bap.intern.shopee.dto.BaseRes;
import com.bap.intern.shopee.dto.order.OrderItemReq;
import com.bap.intern.shopee.dto.order.PostOrderRes;
import com.bap.intern.shopee.service.OrderService;
import com.bap.intern.shopee.util.AuthUtil;

import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	@Autowired
	AuthUtil authUtil;
	
	@PostMapping()
	public ResponseEntity<?> postOrder(@Valid @RequestBody List<OrderItemReq> orderItemReqList, Authentication authentication) {
		int userId = authUtil.getUserId(authentication);
		return new ResponseEntity(orderService.postOrder(userId, orderItemReqList), HttpStatus.CREATED);
	}
	
	@GetMapping("/{orderId}/accept")
	public ResponseEntity<?> getOrder(@PathVariable("orderId") int orderId) {
		BaseRes res = orderService.acceptOrder(orderId);
		return new ResponseEntity(res, HttpStatus.CREATED);
	}
}
