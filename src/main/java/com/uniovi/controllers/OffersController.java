package com.uniovi.controllers;

import java.security.Principal;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.services.OffersService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddOfferFormValidator;

@Controller
public class OffersController {

	@Autowired
	private OffersService offersService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private AddOfferFormValidator addValidator;
	
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/offer/list")
	public String getList(Model model,Pageable pageable, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		if (searchText != null && !searchText.isEmpty()) {
		offers = offersService.searchOfferByDescriptionAndTitle(pageable, searchText);
		} else {
		offers = offersService.getAllOffers(pageable);
		}
		model.addAttribute("offerList", offers.getContent());
		model.addAttribute("page", offers);
		return "offer/list";
	}

	@RequestMapping(value = "/offer/add", method = RequestMethod.POST)
	public String setoffer(@Validated Offer offer, BindingResult result,Principal principal) {
		addValidator.validate(offer, result);
		if(result.hasErrors())
		{
			return "/offer/add";
		}
		String email = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		offer.setUser(user);
		offer.setDate(new Date());
		offersService.addOffer(offer);
		return "redirect:/offer/my";
	}

	@RequestMapping("/offer/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("offer", offersService.getOffer(id));
		return "offer/details";
	}

	@RequestMapping(value = "/offer/add")
	public String getOffer(Model model) {
		model.addAttribute("offer", new Offer());
		return "offer/add";
	}
	
	@RequestMapping(value = "/offer/my")
	public String getMyOffers(Model model,Principal principal) {
		String email = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		
		model.addAttribute("offerList", offersService.getOffersForUser(user));
		return "offer/my";
	}
	

	@RequestMapping(value = "/offer/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("offer", offersService.getOffer(id));
		return "offer/edit";
	}

	@RequestMapping(value = "/offer/delete/{id}")
	public String delete(@PathVariable Long id) {
		offersService.deleteOffer(id);
		return "redirect:/offer/my";
	}

	@RequestMapping("/offer/list/update")
	public String updateList(Model model, Pageable pageable, Principal principal){
	Page<Offer> offers = offersService.getAllOffers(pageable);
	model.addAttribute("offerList", offers.getContent() );
	return "offer/list :: tableOffers";
	}

	@RequestMapping(value = "/offer/{id}/purchase", method = RequestMethod.GET)
	public String setResendTrue(Model model, @PathVariable Long id) {
		offersService.setOfferPurchased(true, id);
		return "redirect:/offer/list";
	}

	@RequestMapping(value = "/offer/{id}/nopurchase", method = RequestMethod.GET)
	public String setResendFalse(Model model, @PathVariable Long id) {
		offersService.setOfferPurchased(false, id);
		return "redirect:/offer/list";
	}
	
	@RequestMapping("/offer/purchased")
	public String getOffersPurchased(Model model,Principal principal) {
		String email = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		
		model.addAttribute("offerList", user.getOffersPurchased() );
		return "/offer/purchased";
	}

}
