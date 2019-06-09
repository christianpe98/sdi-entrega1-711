package com.uniovi.controllers;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.User;
import com.uniovi.service.SecurityService;
import com.uniovi.service.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UserController {

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/login")
	public String login(ServletRequest request, Model model) {

		Map<String, String[]> paramMap = request.getParameterMap();

		if (paramMap.containsKey("error")) {
			model.addAttribute("loginError", true);
		}

		return "login";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		usersService.addUser(user);
		logger.info("Se ha registrado un nuevo usuario: " + user.getEmail());
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:/user/profile";
	}

	@RequestMapping("/user/profile")
	public String profile(Model model) {
		User usuario = usersService.usuarioActual();
		String email = "DESCONOCIDO";
		if (usuario != null) {
			email = usuario.getEmail();
		}
		logger.info("El usuario " + email + " ha accedido a su perfil");
		model.addAttribute("user", usuario);
		return "user/profile";
	}

}
