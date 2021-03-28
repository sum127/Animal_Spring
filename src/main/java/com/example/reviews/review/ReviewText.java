package com.example.reviews.review;


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


@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity 
public class ReviewText {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String nickname;
	private String password;
	private String content;
	
	@OneToMany
	@JoinColumn(name="textId")
	private List<ReviewPicture> files;

	
	
}
