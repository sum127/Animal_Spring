//package com.example.reviews.before;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.security.NoSuchAlgorithmException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.reviews.configuration.ApiConfiguration;
//import com.example.reviews.review.ReviewPictureRepository;
//import com.example.reviews.review.ReviewText;
//import com.example.reviews.review.ReviewTextRepository;
//
//@RestController
//public class AdoptionController {
//
//	private AdoptionInfoRepository repo;
//	private AdoptionImgRepository imgRepo;
//
//	// 사진 저장할경로
////	private final Path FILE_PATH = Paths.get("review_picture_file");
//
////	@Autowired
////	private ApiConfiguration apiConfig;
//
//	// Repository
//	@Autowired
//	public AdoptionController(AdoptionImgRepository imgRepo, AdoptionInfoRepository repo) {
//		this.repo = repo;
//		this.imgRepo = imgRepo;
//	}
//
//	// 내용추가
////	@PostMapping(value = "/reviews")
////	public Adoption adoption(@RequestBody Adoption adoption) throws NoSuchAlgorithmException {
////
////		repo.save(adoption);
////
////		return adoption;
////	}
//}
