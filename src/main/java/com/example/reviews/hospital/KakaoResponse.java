package com.example.reviews.hospital;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoResponse {
	private List<documents> documents;

	@Data
	@NoArgsConstructor
	class documents {
		private String x;
		private String y;
	}
}
