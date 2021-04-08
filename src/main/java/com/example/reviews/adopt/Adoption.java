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
	private String requestNo;
	private long animalId;
	private String animalImg;
	private String noticeNo;
	private String name;
	private String mobile;
	private String email;
	private String gender;
	private String address;
	private String job;
	private String familyMember;
	private String familyAgreed;
	private String petAtHome;
	private String petDetails;
	private String houseType;
	private String reason;
	private String status;
	
	

}
