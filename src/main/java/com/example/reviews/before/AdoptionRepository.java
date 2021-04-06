package com.example.reviews.before;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
	public List<Adoption> findByNameAndMobile(String name, String mobile);
}