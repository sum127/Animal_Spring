package com.example.reviews.hospital;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class HospitalService {

	private String serviceKey = "6a516575656464643130346d53704943";
	private String kakaoServieceKey = "df5fe9f1c9851a8a177782f650e29acf";
	private HospitalRepository repo;

	@Autowired
	public HospitalService(HospitalRepository repo) {
		this.repo = repo;
	}

	// 매시 30분 되면 실행(cron= "0 30 0 0 0")
	// 주기는 31분마다
	@Scheduled(fixedRate = 1000 * 60 * 60 * 24)
	public void requestHospital() throws IOException {
		getHospital();
	}

	private void getHospital() throws IOException {

		StringBuilder builder = new StringBuilder();
		builder.append("http://openapi.seoul.go.kr:8088/");
		builder.append(serviceKey);
		builder.append("/json/LOCALDATA_020301/1/130/");

		// string을 url로 생성
		URL url = new URL(builder.toString());
		// url을 연결함
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// 접속으로부터 스트림을 읽어서 바이트타입 배열로 대입
		byte[] result = con.getInputStream().readAllBytes();

		// 응답의 한줄을 문자열로 읽음
		String data = new String(result);
		System.out.println("위에 data");
		System.out.println(data);

		// data 를 DustHourlyResponse형식으로 변환
		HospitalResponse response = new Gson().fromJson(data, HospitalResponse.class);

		for (HospitalResponse.rows item : response.getLOCALDATA_020301().getRow()) {

			Hospital hospital = new Hospital(item);
			String type = hospital.getSITEWHLADDR();
			type = type.replace("[", "");
			type = type.replace("]", "");
			String[] typeArray = type.split(" ");
			hospital.setGuName(typeArray[1]);

			String type1 = hospital.getX();
			String type2 = hospital.getY();
			hospital.setX(type1.trim());
			hospital.setY(type2.trim());

			String a = hospital.getTRDSTATEGBN();
			if (a.equals("01")) {

				Hospital savedAnimal = repo.findByBPLCNM(hospital.getBPLCNM());
				if (savedAnimal == null) {
					if (hospital.getX() == null && hospital.getY() == null) {
						repo.save(hospital);
					} else {
						getMap(hospital.getX(), hospital.getY(), hospital);
					}

				}
			}

		}

		// 엔티티에 인설트

	}

	private void getMap(String x, String y, Hospital hospital) throws IOException {
		StringBuilder kakaoBuilder = new StringBuilder();
		kakaoBuilder.append("https://dapi.kakao.com/v2/local/geo/transcoord.");
		kakaoBuilder.append("json?input_coord=TM&output_coord=WGS84&");
		kakaoBuilder.append("x=" + x + "&y=" + y);

		URL url = new URL(kakaoBuilder.toString());
		// url을 연결함
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.addRequestProperty("Authorization", "KakaoAK " + kakaoServieceKey);

		byte[] result = connection.getInputStream().readAllBytes();

		String data = new String(result);
		KakaoResponse response = new Gson().fromJson(data, KakaoResponse.class);
		for (KakaoResponse.documents item : response.getDocuments()) {

			hospital.setAfterX(item.getX());
			hospital.setAfterY(item.getY());
			repo.save(hospital);
		}
	}

}
