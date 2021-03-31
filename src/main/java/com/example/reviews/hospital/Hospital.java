package com.example.reviews.hospital;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// 영업상태코드
	private String TRDSTATEGBN;
	// 영업상태명
	private String TRDSTATENM;
	// 지번주소
	private String SITEWHLADDR;
	// ㅁㅁ구
	private String guName;
	// 사업장명
	private String BPLCNM;
	// 데이터갱신일자
	private String UPDATEDT;

	private String X;

	private String Y;

	private String afterX;

	private String afterY;

	public Hospital(HospitalResponse.rows res) {
		this.TRDSTATEGBN = res.getTRDSTATEGBN();
		this.TRDSTATENM = res.getTRDSTATENM();
		this.SITEWHLADDR = res.getSITEWHLADDR();
		this.BPLCNM = res.getBPLCNM();
		this.UPDATEDT = res.getUPDATEDT();
		this.X = res.getX();
		this.Y = res.getY();
	}

	public Hospital(KakaoResponse.documents res) {
		this.afterX = res.getX();
		this.afterY = res.getY();
	}

}
