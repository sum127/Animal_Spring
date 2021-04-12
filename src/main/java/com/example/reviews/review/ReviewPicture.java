package com.example.reviews.review;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReviewPicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long textId;
	private String fileName;
	private String contentType;

	@Transient
	private String dataUrl;
}
