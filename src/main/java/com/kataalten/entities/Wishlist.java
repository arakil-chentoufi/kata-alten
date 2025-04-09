package com.kataalten.entities;

import java.util.ArrayList;
import java.util.List;

import com.kataalten.auth.model.AppUser;
import com.kataalten.enums.InventoryStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wish_list", schema = "kata_schema")
@Data
@NoArgsConstructor
public class Wishlist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private AppUser appUser;

	@ManyToMany
	private List<Product> products = new ArrayList<>();

}
