package com.example.reviews.adopt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reviews.hospital.Hospital;
import com.example.reviews.review.ReviewPicture;
import com.example.reviews.review.ReviewText;

@RestController
public class AdoptionController {

	private AdoptionRepository repo;

	@Autowired
	public AdoptionController(AdoptionRepository repo) {
		this.repo = repo;
	}

	@GetMapping(value = "/adoption/keyword")
	public List<Adoption> getNickname(@RequestParam("name") String name,
			@RequestParam("mobile") String mobile) {
		
		List<Adoption> list = repo.findByNameAndMobile(name,mobile);
		System.out.println(list);

		return list;
	}
	

}
