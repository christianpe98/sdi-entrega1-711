package com.uniovi.controllers;

import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		User usuario = usersService.usuarioActual();

		model.addAttribute("offerList", offersService.getOffersForUser(usuario));
		logger.info("El usuario " + usuario.getEmail() + " ha a la vista para ver sus ofertas publicadas");
		model.addAttribute("user", usuario);
		return "offer/my";
	}

	@RequestMapping(value = "/offer/add")
	public String getOffer(Model model) {
		User usuario = usersService.usuarioActual();
		logger.info("El usuario " + usuario.getEmail() + " ha accedido a la vista para añadir ofertas");
		model.addAttribute("user", usuario);
		model.addAttribute("offer", new Offer());
		return "offer/add";
	}

	@RequestMapping(value = "/offer/add", method = RequestMethod.POST)
	public String setoffer(@Validated Offer offer, BindingResult result) {
		addValidator.validate(offer, result);
		if (result.hasErrors()) {
			return "redirect:/offer/add";
		}
		User user = usersService.usuarioActual();
		offer.setUser(user);
		offersService.addOffer(offer);
		logger.info("El usuario " + user.getEmail() + " ha añadido la oferta: " + offer.getTitle());
		return "redirect:/offer/my";
	}

	@RequestMapping(value = "/offer/delete/{id}")
	public String delete(@PathVariable Long id) {
		User usuario = usersService.usuarioActual();
		try {
			offersService.deleteOffer(id);
			logger.info("El usuario " + usuario.getEmail() + " ha eliminado la oferta con id: " + id);
			return "redirect:/offer/my";
		} catch (IllegalArgumentException arg) {// Si el usuario no es el propiertario de la oferta saltará error
			logger.info("El usuario " + usuario.getEmail() + " ha intentado eliminar la oferta con id: " + id);
			return "error";
		}
	}

	@RequestMapping("/offer/purchased")
	public String getOffersPurchased(Model model) {
		User user = usersService.usuarioActual();
		model.addAttribute("user", user);
		model.addAttribute("offerList", user.getOffersPurchased());

		logger.info("El usuario " + user.getEmail() + " ha accedido a la vista para ver sus ofertas compradas");
		return "/offer/purchased";
	}

	@RequestMapping("/offer/list")
	public String getList(Model model, Pageable pageable,
			@RequestParam(value = "searchText", required = false) String searchText, HttpSession session) {
		User user = usersService.usuarioActual();
		Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
		if (searchText != null && !searchText.isEmpty()) {
			offers = offersService.searchOfferByDescriptionAndTitle(pageable, searchText);
			logger.info("El usuario " + user.getEmail() + " ha buscado la oferta: " + searchText);
			model.addAttribute("searchText", searchText);
		} else {
			offers = offersService.getAllOffers(pageable);
			model.addAttribute("searchText", "");
		}
		if (session.getAttribute("errorCompra") != null) {
			model.addAttribute(session.getAttribute("errorCompra").toString(), true);
			session.removeAttribute("errorCompra");
		}
		model.addAttribute("offerList", offers.getContent());
		model.addAttribute("page", offers);
		model.addAttribute("user", user);
		return "offer/list";
	}

	@RequestMapping(value = "/offer/{id}/purchase", method = RequestMethod.GET)
	public String setResendTrue(HttpSession session, @PathVariable Long id) {
		try {
			offersService.setOfferPurchased(true, id);
		} catch (IllegalArgumentException arg) {
			session.setAttribute("errorCompra", arg.getMessage());
		}
		return "redirect:/offer/list";
	}

	@RequestMapping(value = "/offer/{id}/nopurchase", method = RequestMethod.GET)
	public String setResendFalse(Model model, @PathVariable Long id) {
		// offersService.setOfferPurchased(false, id);
		return "redirect:/offer/list";
	}

}
