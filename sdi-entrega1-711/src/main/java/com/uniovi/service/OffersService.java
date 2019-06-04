package com.uniovi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.repository.OfferRepository;

@Service
public class OffersService {

	@Autowired
	private OfferRepository ofertaRepository;
	
	public void update(Offer oferta) {
		ofertaRepository.save(oferta);
	}

}
