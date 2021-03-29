package com.example.reviews.hospital;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class HospitalService {

	private String serviceKey = "6a516575656464643130346d53704943";

	private HospitalRepository repo;

	public HospitalService(HospitalRepository repo) {
		this.repo = repo;
	}

	// 매시 30분 되면 실행(cron= "0 30 0 0 0")
	// 주기는 31분마다
	@Scheduled(fixedRate = 1000 * 60 * 1)
	public void requestHospital() throws IOException {
		getHospital();
	}

	private void getHospital() throws IOException {

		StringBuilder builder = new StringBuilder();
		builder.append("http://openapi.seoul.go.kr:8088/");
		builder.append(serviceKey);
		builder.append("/json/LOCALDATA_020301/1/5/");

		// string을 url로 생성
		URL url = new URL(builder.toString());
		// url을 연결함
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// 접속으로부터 스트림을 읽어서 바이트타입 배열로 대입
		byte[] result = con.getInputStream().readAllBytes();

		// 응답의 한줄을 문자열로 읽음
		String data = new String(result);
		System.out.println(data);

		// data 를 DustHourlyResponse형식으로 변환
		HospitalResponse response = new Gson().fromJson(data, HospitalResponse.class);

		for (HospitalResponse.rows item : response.getLOCALDATA_020301().getRow()) {
			Hospital hospital = new Hospital(item);
			System.out.println(item);
			System.out.println(hospital);
			System.out.println("------------------------------------------------------------------------------");
			String type = hospital.getSITEWHLADDR();
			type = type.replace("[", "");
			type = type.replace("]", "");
			String[] typeArray = type.split(" ");
			hospital.setGuName(typeArray[1]);
			System.out.println(hospital.getGuName());
			System.out.println(hospital.getTRDSTATEGBN());
			if (repo.findAll() == null) {
				repo.save(hospital);
			}

		}

		// 엔티티에 인설트

	}
}
