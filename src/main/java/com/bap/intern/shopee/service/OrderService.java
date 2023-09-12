package com.bap.intern.shopee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.bap.intern.shopee.dto.order.OrderItemReq;
import com.bap.intern.shopee.dto.order.PostOrderReq;
import com.bap.intern.shopee.dto.order.PostOrderRes;
import com.bap.intern.shopee.entity.Order;
import com.bap.intern.shopee.entity.Order.OrderStatus;
import com.bap.intern.shopee.entity.OrderItem;
import com.bap.intern.shopee.entity.Product;
import com.bap.intern.shopee.entity.User;
import com.bap.intern.shopee.exception.customException.ProductException;
import com.bap.intern.shopee.repository.OrderItemRepository;
import com.bap.intern.shopee.repository.OrderRepository;
import com.bap.intern.shopee.repository.ProductRepository;
import com.bap.intern.shopee.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderItemRepository orderItemRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserRepository userRepository;

	public PostOrderRes postOrder (int userId, PostOrderReq req) {
		User user = userRepository.findById(userId).get();
		List<OrderItemReq> orderItemReqList = req.getOrderItemReqList();
		
		Order newOrder = Order.builder().user(user).status(OrderStatus.UNACCEPTED)
										.createdAt(new Date()).build();
		double totalMoney = 0;
		List<OrderItem> orderItems = new ArrayList<>();
		for (OrderItemReq orderItemReq : orderItemReqList) {
			Optional<Product> productOptional = productRepository.findById(orderItemReq.getProductId());
			
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				OrderItem orderItem = OrderItem.builder().order(newOrder).product(product)
												.quantity(orderItemReq.getQuantity()).price(product.getPrice())
												.build();
				orderItemRepository.save(orderItem);
				orderItems.add(orderItem);
				totalMoney += orderItemReq.getQuantity() * product.getPrice();
			} else throw new ProductException("productId " + orderItemReq.getProductId() + " is not existed");
		}
		
		newOrder.setTotalMoney(totalMoney);
		newOrder.setOrderItems(orderItems);
		orderRepository.save(newOrder);
			
		return new PostOrderRes(newOrder);
	}

	public PostOrderRes getOrder(int orderId) {
		Order order = orderRepository.findById(orderId).get();
		return new PostOrderRes(order);
	}
}
