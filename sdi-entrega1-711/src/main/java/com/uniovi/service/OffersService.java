package com.uniovi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.repository.OffersRepository;

@Service
public class OffersService {

	@Autowired
	private OffersRepository offersRepository;

	@Autowired
	private UsersService userService;

	public Offer getOffer(Long id) {
		Offer offerObtained = offersRepository.findById(id).get();
		return offerObtained;
	}

	public void addOffer(Offer offer) {
		offersRepository.save(offer);
	}

	public void deleteOffer(Long id) {
		User usuarioActual = userService.usuarioActual();
		Offer oferta = getOffer(id);
		if (oferta == null || !oferta.getUser().equals(usuarioActual)) {
			throw new IllegalArgumentException();
		}
		offersRepository.deleteById(id);
	}

	public Page<Offer> getAllOffers(Pageable pageable) {
		return offersRepository.findAll(pageable);
	}

	public List<Offer> getAllOffers() {
		return offersRepository.findAll();
	}

	public void setOfferPurchased(boolean purchase, Long id) {

		User user = userService.usuarioActual();
		Offer offer = offersRepository.findById(id).get();
		if (user.equals(offer.getUser())) // El propietario no puede comprar su propia oferta
		{
			throw new IllegalArgumentException("esPropietario");
		}
		if (user.getBalance() < offer.getPrice())// El propietario no tiene dinero para hacer la compra
		{
			throw new IllegalArgumentException("noDinero");
		}

		if (purchase) // el usuario quiere comprar
		{
			user.decrementBalance(offer.getPrice());
			offersRepository.updatePurchase(purchase, id, user);
			user.getOffersPurchased().add(offer);
			userService.update(user);
		}

	}

	public void update(Offer oferta) {
		offersRepository.save(oferta);
	}

	public Page<Offer> searchOfferByDescriptionAndTitle(Pageable pageable, String searchText) {
		searchText = "%" + searchText + "%";
		return offersRepository.searchByTitle(pageable, searchText);
	}

	public List<Offer> getOffersForUser(User user) {
		return offersRepository.findAllByUser(user);
	}

	public void deleteAll() {
		offersRepository.deleteAll();
	}

}
