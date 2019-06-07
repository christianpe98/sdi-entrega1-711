package com.uniovi.controllers;

import java.security.Principal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
		logger.info("El usuario " + usuario.getEmail() + " ha a la vista para ver sus ofertas publicadas");
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
			return "redirect:/offer/add";
		}
		User user = usersService.usuarioActual();
		offer.setUser(user);
		offersService.addOffer(offer);
		logger.info("El usuario " + user.getEmail() + " ha añadido la oferta: "+offer.getTitle());
		return "redirect:/offer/my";
	}
	
	@RequestMapping(value = "/offer/delete/{id}")
	public String delete(@PathVariable Long id) {
		User usuario=usersService.usuarioActual();
		try {
		offersService.deleteOffer(id);
		logger.info("El usuario " + usuario.getEmail() + " ha eliminado la oferta con id: "+id);
		return "redirect:/offer/my";
		}catch(IllegalArgumentException arg) {//Si el usuario no es el propiertario de la oferta  saltará error
			logger.info("El usuario " + usuario.getEmail() + " ha intentado eliminar la oferta con id: "+id);
			return "error";
		}
	}
	
	@RequestMapping("/offer/purchased")
	public String getOffersPurchased(Model model,Principal principal) {
		String email = principal.getName(); // DNI es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		model.addAttribute("user", user);
		model.addAttribute("offerList", user.getOffersPurchased() );
		
		logger.info("El usuario " + user.getEmail() + " ha a la vista para ver sus ofertas compradas");
		return "/offer/purchased";
	}
	
}
