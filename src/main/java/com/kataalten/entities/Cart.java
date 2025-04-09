package com.kataalten.entities;

import java.util.ArrayList;
import java.util.List;

import com.kataalten.auth.model.AppUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart", schema = "kata_schema")
@Data
@NoArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private AppUser appUser;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items = new ArrayList();

}
