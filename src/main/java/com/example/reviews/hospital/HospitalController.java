package com.example.reviews.hospital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {
	private HospitalRepository hospitalRepo;

	@Autowired
	public HospitalController(HospitalRepository hospitalRepo) {
		this.hospitalRepo = hospitalRepo;
	}

	@GetMapping(value = "/hospital")
	public List<Hospital> getReviews() {
		List<Hospital> list = hospitalRepo.findAll(Sort.by("id").descending());

		return list;

	}

	@GetMapping(value = "/hospital/paging")
	public List<Hospital> getHospital(@RequestParam("page") int page, @RequestParam("size") int size) {
		// �쟾泥� 紐⑸줉 議고쉶, �럹�씠吏�
		return hospitalRepo.findAll(PageRequest.of(page, size)).toList();
	}

}
