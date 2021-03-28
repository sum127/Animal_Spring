package com.example.reviews.review;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewTextRepository extends JpaRepository<ReviewText, Long>{
	public List<ReviewText> findByNickname(String nickname);
	
	public List<ReviewText> findByTitle(String keyword);
	public List<ReviewText> findByPassword(String keyword);
}
