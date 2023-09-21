package com.bap.intern.shopee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bap.intern.shopee.dto.BaseRes;
import com.bap.intern.shopee.dto.order.OrderItemReq;
import com.bap.intern.shopee.dto.order.PostOrderRes;
import com.bap.intern.shopee.entity.Order;
import com.bap.intern.shopee.entity.Order.OrderStatus;
import com.bap.intern.shopee.entity.OrderItem;
import com.bap.intern.shopee.entity.Product;
import com.bap.intern.shopee.entity.User;
import com.bap.intern.shopee.exception.customException.OrderNotExistedException;
import com.bap.intern.shopee.exception.customException.ProductNotExistedException;
import com.bap.intern.shopee.repository.OrderItemRepository;
import com.bap.intern.shopee.repository.OrderRepository;
import com.bap.intern.shopee.repository.ProductRepository;
import com.bap.intern.shopee.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderItemRepository orderItemRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserRepository userRepository;

	public PostOrderRes postOrder (int userId, List<OrderItemReq> orderItemReqList) {
		User user = userRepository.findById(userId).get();
		
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
			} else throw new ProductNotExistedException();
		}
		
		newOrder.setTotalMoney(totalMoney);
		newOrder.setOrderItems(orderItems);
		orderRepository.save(newOrder);
		
		log.info("Order successfully, total money: " + totalMoney);
		return new PostOrderRes(newOrder);
	}

	public BaseRes acceptOrder(int orderId) {
		Optional<Order> orderOptional = orderRepository.findById(orderId);
		if (orderOptional.isPresent()) {
			
			Order order = orderOptional.get();
			order.setStatus(OrderStatus.ACCEPTED);
			orderRepository.save(order);

			log.info("accept order (id = " + orderId + ") successfully");
			return new BaseRes(200, "accept order (id = " + orderId + ") successfully");
		} else throw new OrderNotExistedException();
	}
}
