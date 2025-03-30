package com.bap.intern.shopee.entity;

import java.io.Serializable;
import java.util.List;

import com.bap.intern.shopee.interceptor.ProductInterceptor;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Entity
//@EntityListeners(ProductInterceptor.class)
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Product implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<OrderItem> orderItems;
}
