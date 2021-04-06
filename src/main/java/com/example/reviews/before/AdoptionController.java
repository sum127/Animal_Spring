package com.example.reviews.before;

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

	// 사진 저장할경로
//	private final Path FILE_PATH = Paths.get("review_picture_file");

//	@Autowired
//	private ApiConfiguration apiConfig;

	@Autowired
	public AdoptionController(AdoptionRepository repo) {
		this.repo = repo;
	}

	// 내용추가
	@GetMapping(value = "/adoption")
	public List<Adoption> adoption()  {
		List<Adoption> list = repo.findAll(Sort.by("id").descending());

		return list;
		
	}
	//글저장시 noticeNo에 해당reviewtext아이디 저장되게하고
	//noticeNo로 찾는컨트롤러설정
	// 뷰에서 그걸로 찾아서 이미지추가해주기
	
	@GetMapping(value = "/adoption/keyword")
	public List<Adoption> getNickname(@RequestParam("name") String name,
			@RequestParam("mobile") String mobile) {
		
		List<Adoption> list = repo.findByNameAndMobile(name,mobile);
		System.out.println(list);

		return list;
	}
	

}
