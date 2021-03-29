package com.example.reviews.hospital;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HospitalResponse {
	private LOCALDATA_020301 LOCALDATA_020301;

	@Data
	@NoArgsConstructor
	class LOCALDATA_020301 {
		private List<rows> row;
	}

	@Data
	@NoArgsConstructor
	class rows {
		// 영업상태코드
		private String TRDSTATEGBN;
		// 영업상태명
		private String TRDSTATENM;
		// 지번주소
		private String SITEWHLADDR;
		// 사업장명
		private String BPLCNM;
		// 데이터갱신일자
		private String UPDATEDT;
	}
}
