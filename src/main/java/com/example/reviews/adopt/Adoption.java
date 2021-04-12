package com.example.reviews.adopt;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adoption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long adoptionId;
	private long animalId;
	private String name;
	private String mobile;
	private String animalImg;
	private String status;
	
	

}
