package com.bap.intern.shopee.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Table(name="orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@RedisHash("orders")
public class Order implements Serializable {
	
	public enum OrderStatus {
		UNACCEPTED,
		ACCEPTED,
		REJECTED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "total_money")
	private double totalMoney;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column(name = "created_at")
	private Date createdAt;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private List<OrderItem> orderItems;
}
