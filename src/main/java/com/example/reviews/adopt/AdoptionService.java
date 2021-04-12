package com.example.reviews.adopt;

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


	@RabbitListener(bindings = {
			@QueueBinding(exchange = @Exchange(name = "amq.topic", type = "topic"), value = @Queue(value = "get.adoption"), key = {
					"adoption" }) })

	public void receiveOrder3(Adoption request) {
		System.out.println(request);
		if(request.getStatus().equals("입양완료")) {
		Adoption info = Adoption.builder()
				.adoptionId(request.getAdoptionId())
				.animalId(request.getAnimalId())
				.name(request.getName())
				.mobile(request.getMobile())
				.animalImg(request.getAnimalImg())
				.status(request.getStatus())
				.build();
		
		repo.save(info);
		}
	}

}
