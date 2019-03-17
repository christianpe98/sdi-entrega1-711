package com.uniovi.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.repositories.OffersRepository;

@Service
public class OffersService {

	@Autowired
	private OffersRepository offersRepository;

	@Autowired
	private UsersService userService;
	
	@Autowired
	private HttpSession httpSession;

	public Offer getOffer(Long id) {
		@SuppressWarnings("unchecked")
		Set<Offer> consultedList = (Set<Offer>) httpSession.getAttribute("consultedList");
		if (consultedList == null) {
			consultedList = new HashSet<Offer>();
		}
		Offer offerObtained = offersRepository.findById(id).get();
		consultedList.add(offerObtained);
		httpSession.setAttribute("consultedList", consultedList);
		return offerObtained;
	}

	public void addOffer(Offer offer) {
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		offersRepository.save(offer);
	}

	public void deleteOffer(Long id) {
		offersRepository.deleteById(id);
	}

	public Page<Offer> getAllOffers(Pageable pageable)
	{
		return offersRepository.findAll(pageable);
	}
	
	public List<Offer> getAllOffers()
	{
		return offersRepository.findAll();
	}
	
	public void setOfferPurchased(boolean purchase, Long id) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.getUserByEmail(email);
		Offer offer = offersRepository.findById(id).get();
		if(!user.equals(offer.getUser())) // un usuario no puede comprar su propia oferta
		{
			if(offer.getPurchased()!=true) //no esta comprada
			{
				if (user.getBalance()>=offer.getPrice()) { //el usuario tiene el dinero
					if(purchase == true) //el usuario quiere comprar la oferta
					{
						user.decrementBalance(offer.getPrice());
						offersRepository.updatePurchase(purchase, id,user);
						user.getOffersPurchased().add(offer);
					}
				}
			}
		}		

	}

	public void update(Offer oferta)
	{
		offersRepository.save(oferta);
	}
	
	public Page<Offer> searchOfferByDescriptionAndTitle(Pageable pageable, String searchText)
	{
		searchText = "%" + searchText + "%";
		return offersRepository.searchByTitle(pageable, searchText);
	}
	
	public List<Offer> getOffersForUser(User user) {
		return offersRepository.findAllByUser(user);
	}


}
