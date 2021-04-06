package com.example.reviews.before;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdoptionService {

	private AdoptionRepository repo;

	@Autowired
	public AdoptionService(AdoptionRepository repo) {
		this.repo = repo;
	}

//	@RabbitListener(queues = "adoption.status")
//
//	public void receiveOrder(Animal animal) {
//		System.out.println(animal);
//		AdoptionInfo adoption = AdoptionInfo.builder().age(animal.getAge()).sexCd(animal.getSexCd()).kind(animal.getKind())
//				.weight(animal.getWeight()).build();
//
//		adoptionRepo.save(adoption);
//	}

	@RabbitListener(bindings = {
			@QueueBinding(exchange = @Exchange(name = "amq.topic", type = "topic"), value = @Queue(value = "get.adoption"), key = {
					"adoption" }) })

	public void receiveOrder3(Adoption request) {
		System.out.println(request);
		Adoption info = Adoption.builder().adoptionId(request.getAdoptionId()).requestNo(request.getRequestNo())
				.animalId(request.getAnimalId()).animalImg(request.getAnimalImg()).noticeNo(request.getNoticeNo())
				.name(request.getName()).mobile(request.getMobile()).email(request.getEmail())
				.gender(request.getGender()).address(request.getAddress()).job(request.getJob())
				.familyAgreed(request.getFamilyAgreed()).petAtHome(request.getPetAtHome())
				.petDetails(request.getPetDetails()).houseType(request.getHouseType()).reason(request.getReason())
				.status(request.getStatus()).build();

		repo.save(info);
	}

}
