package com.bap.intern.shopee.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
	
	public enum Role {
		ADMIN("ROLE_ADMIN"),
		SHOP("ROLE_SHOP"),
		CUSTOMER("ROLE_CUSTOMER");
		
		private final String name;
		Role(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public static Role of(int roleOrdinal) {
	        return Stream.of(Role.values())
	                .filter(role -> role.ordinal() == roleOrdinal)
	                .findFirst()
	                .orElseThrow(IllegalArgumentException::new);
	          }
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;
	
	@ElementCollection(targetClass = Role.class)
//	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id")) cái này thay @JoinTable cũng đc nhe
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	@Fetch(FetchMode.JOIN) // Để cài đặt fetch eager
	private Set<Role> roles;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Order> orders;
}
