package com.example.reviews.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewPictureRepository extends JpaRepository<ReviewPicture, Long> {
	List<ReviewPicture> findByTextId(long textId);
}
