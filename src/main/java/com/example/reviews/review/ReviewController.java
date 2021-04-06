package com.example.reviews.review;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.reviews.configuration.ApiConfiguration;

@RestController
public class ReviewController {

	private ReviewPictureRepository pictureRepo;
	private ReviewTextRepository textRepo;

	// 사진 저장할경로
	private final Path FILE_PATH = Paths.get("review_picture_file");

	@Autowired
	private ApiConfiguration apiConfig;

	// Repository
	@Autowired
	public ReviewController(ReviewPictureRepository pictureRepo, ReviewTextRepository textRepo) {
		this.pictureRepo = pictureRepo;
		this.textRepo = textRepo;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 전체조회
	@GetMapping(value = "/reviews")
	public List<ReviewText> getReviews() {
		textRepo.findAll(Sort.by("id").descending());

		List<ReviewText> list = textRepo.findAll(Sort.by("id").descending());
		for (ReviewText reviewText : list) {
			for (ReviewPicture file : reviewText.getFiles()) {
				file.setDataUrl(apiConfig.getBasePath() + "/review-picture-file/" + file.getId());
			}
		}

		return list;

	}

	// 해당 id 사진조회
//	@GetMapping(value = "/reviews/{id}/review-picture-file")
//	public List<ReviewPicture> getReviewPictures(@PathVariable("id") long id, HttpServletResponse res) {
//		if (textRepo.findById(id).orElse(null) == null) {
//			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
//			return null;
//		}
//		List<ReviewPicture> reviewPictures = pictureRepo.findByTextId(id);
//
//		return reviewPictures;
//	}

	// 닉네임으로 조회
	// http://localhost:8080/reviews/search/nickname?keyword=揶쏉옙
	@GetMapping(value = "/reviews/search/nickname")
	public List<ReviewText> getReviewsByNickname(@RequestParam("keyword") String keyword) {

		List<ReviewText> list = textRepo.findByNickname(keyword);
		for (ReviewText reviewText : list) {
			for (ReviewPicture file : reviewText.getFiles()) {
				file.setDataUrl(apiConfig.getBasePath() + "/review-picture-file/" + file.getId());
			}
		}

		return list;
	}

	// id로 조회
	@GetMapping(value = "/reviews/{id}")
	public ReviewText getReviewsById(@PathVariable("id") long id, HttpServletResponse res) {
		if (textRepo.findById(id).orElse(null) == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		ReviewText reviewText = textRepo.findById(id).orElse(null);

		for (ReviewPicture file : reviewText.getFiles()) {
			file.setDataUrl(apiConfig.getBasePath() + "/review-picture-file/" + file.getId());
		}

		return reviewText;
	}

	// title로 조회
	@GetMapping(value = "/reviews/search/title")
	public List<ReviewText> getReviewsByTitle(@RequestParam("keyword") String keyword) {

		List<ReviewText> list = textRepo.findByTitle(keyword);
		for (ReviewText reviewText : list) {
			for (ReviewPicture file : reviewText.getFiles()) {
				file.setDataUrl(apiConfig.getBasePath() + "/review-picture-file/" + file.getId());
			}
		}

		return list;
	}

// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// (조회)

	// nickname 중복체크
	@GetMapping(value = "/reviews/nickname/check")
	public boolean getcheck(@RequestParam("keyword") String keyword) {

		List<ReviewText> list = textRepo.findByNickname(keyword);
		if (list.size() > 0) {
			return true;
		}

		return false;
	}

	// 비밀번호 닉네임 중복체크
	@GetMapping(value = "/reviews/{nickname}/check")
	public boolean getPasswordCheck(@PathVariable("nickname") String nickname, @RequestParam("keyword") String keyword)
			throws NoSuchAlgorithmException {

		List<ReviewText> pass = textRepo.findByPassword(sha256(keyword));
		List<ReviewText> name = textRepo.findByNickname(nickname);
		if (name.size() > 0) {
			if (pass.size() > 0) {
				return true;
			} else if (pass.size() == 0) {
				return false;
			}
		}
		return false;
	}

// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// (체크)

	// 내용추가
	@PostMapping(value = "/reviews")
	public ReviewText addReviews(@RequestBody ReviewText reviewText) throws NoSuchAlgorithmException {

		reviewText.setPassword(sha256(reviewText.getPassword()));
		textRepo.save(reviewText);

		return reviewText;
	}

	// 암호화
	public static String sha256(String msg) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(msg.getBytes());

		return bytesToHex(md.digest());
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			builder.append(String.format("%02x", b));
		}

		return builder.toString();

	}

	// 사진추가
	@PostMapping(value = "/reviews/{id}/review-picture-file")
	public ReviewPicture addReviewPicture(@PathVariable("id") long id, @RequestPart("data") MultipartFile file,
			HttpServletResponse res) throws IOException {

		if (textRepo.findById(id).orElse(null) == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		if (!Files.exists(FILE_PATH)) {
			Files.createDirectories(FILE_PATH);
		}
		FileCopyUtils.copy(file.getBytes(), new File(FILE_PATH.resolve(file.getOriginalFilename()).toString()));

		ReviewPicture reviewPicture = ReviewPicture.builder().textId(id).fileName(file.getOriginalFilename())
				.contentType(file.getContentType()).build();

		pictureRepo.save(reviewPicture);

		return reviewPicture;
	}

// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// (추가)

	// 해당 id 삭제
	@DeleteMapping("/reviews/{id}")
	public boolean removeText(@PathVariable("id") long id, HttpServletResponse res) {

		ReviewText reviewText = textRepo.findById(id).orElse(null);

		if (reviewText == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		List<ReviewPicture> files = pictureRepo.findByTextId(id);
		for (ReviewPicture file : files) {
			pictureRepo.delete(file);
		}
		textRepo.deleteById(id);

		return true;
	}

// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// (삭제)

	// 해당아이디로 사진 삭제
	@DeleteMapping(value = "/reviews/{id}/review-picture-file")
	public boolean removePicture(@PathVariable("id") long id, HttpServletResponse res) {
		if (textRepo.findById(id).orElse(null) == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		List<ReviewPicture> reviewPictures = pictureRepo.findByTextId(id);
		for (ReviewPicture reviewPicture : reviewPictures) {
			pictureRepo.delete(reviewPicture);
			File file = new File(reviewPicture.getFileName());
			if (file.exists()) {
				file.delete();
			}
		}
		return true;
	}

	// 백엔드와 프론트 연동할때 필요한식
	@GetMapping(value = "/review-picture-file/{id}")
	public ResponseEntity<byte[]> getReviewPicture(@PathVariable("id") long id, HttpServletResponse res)
			throws IOException {
		ReviewPicture reviewPicture = pictureRepo.findById(id).orElse(null);

		if (reviewPicture == null) {
			return ResponseEntity.notFound().build();
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", reviewPicture.getContentType() + ";charset=UTF-8");
		// inline: �뀎怨쀫선嚥∽옙, attachement: 占쎄땀占쎌젻獄쏆룄由�
		responseHeaders.set("Content-Disposition",
				"inline; filename=" + URLEncoder.encode(reviewPicture.getFileName(), "UTF-8"));

		return ResponseEntity.ok().headers(responseHeaders)
				.body(Files.readAllBytes(FILE_PATH.resolve(reviewPicture.getFileName())));
	}

// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// (수정)

	// 내용수정
	@PatchMapping(value = "/reviews/{id}")

	public ReviewText modifyReview(@PathVariable("id") long id, @RequestParam("content") String content,
			HttpServletResponse res) {

		ReviewText reviewText = textRepo.findById(id).orElse(null);

		if (reviewText == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		reviewText.setContent(content);

		textRepo.save(reviewText);

		return null;
	}

	// 사진수정
	@PatchMapping(value = "/reviews/{id}/pictures")

	public ReviewText modifyPic(@PathVariable("id") long id, @RequestPart("data") MultipartFile file,
			HttpServletResponse res) throws IOException {

		List<ReviewPicture> reviewPictures = pictureRepo.findByTextId(id);

		System.out.println(reviewPictures);
		for (ReviewPicture reviewPicture : reviewPictures) {
			pictureRepo.delete(reviewPicture);
			File files = new File(reviewPicture.getFileName());
			if (files.exists()) {
				files.delete();
			}
		}

		if (!Files.exists(FILE_PATH)) {
			Files.createDirectories(FILE_PATH);
		}

		FileCopyUtils.copy(file.getBytes(), new File(FILE_PATH.resolve(file.getOriginalFilename()).toString()));

		ReviewPicture reviewPicture = ReviewPicture.builder().textId(id).fileName(file.getOriginalFilename())
				.contentType(file.getContentType()).build();

		pictureRepo.save(reviewPicture);

		return null;
	}

}
