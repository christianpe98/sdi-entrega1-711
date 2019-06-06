package com.uniovi.controllers;

import java.security.Principal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.service.OffersService;
import com.uniovi.service.UsersService;
import com.uniovi.validators.AddOfferFormValidator;

@Controller
public class OfferController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private AddOfferFormValidator addValidator;
	
	@Autowired
	private OffersService offersService;
	
	private Logger logger = LoggerFactory.getLogger(OfferController.class);
	
	@RequestMapping(value = "/offer/my")
	public String getMyOffers(Model model) {
		User usuario=usersService.usuarioActual();
		
		model.addAttribute("offerList", offersService.getOffersForUser(usuario));
		logger.info("El usuario " + usuario.getEmail() + " ha accedido a su perfil");
		model.addAttribute("user", usuario);
		return "offer/my";
	}
	
	@RequestMapping(value = "/offer/add")
	public String getOffer(Model model) {
		User usuario=usersService.usuarioActual();
		logger.info("El usuario " + usuario.getEmail() + " ha accedido a la vista para añadir ofertas");
		model.addAttribute("user", usuario);
		model.addAttribute("offer", new Offer());
		return "offer/add";
	}
	
	@RequestMapping(value = "/offer/add", method = RequestMethod.POST)
	public String setoffer(@Validated Offer offer, BindingResult result) {
		addValidator.validate(offer, result);
		if(result.hasErrors())
		{
			return "/offer/add";
		}
		User user = usersService.usuarioActual();
		offer.setUser(user);
		offersService.addOffer(offer);
		
		logger.info("El usuario " + user.getEmail() + " ha añadido la oferta: "+offer.getTitle());
		return "redirect:/offer/my";
	}
	
	@RequestMapping(value = "/offer/delete/{id}")
	public String delete(@PathVariable Long id) {
		offersService.deleteOffer(id);
		return "redirect:/offer/my";
	}
	
}
