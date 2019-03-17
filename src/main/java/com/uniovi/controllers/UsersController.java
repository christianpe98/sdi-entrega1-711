package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private RolesService rolesService;
	
	@RequestMapping("/user/list")
	public String getListado(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "user/list";
	}
	
	
	@RequestMapping("user/profile")
	public String infoUser(Model model,Principal principal)
	{
		String email =principal.getName();
		User user=usersService.getUserByEmail(email);
		model.addAttribute("user",user);
		return "user/profile";
	}

	@RequestMapping("/user/delete")
	public String getListadoEliminar(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "user/delete";
	}
	
	@RequestMapping(value="/user/delete",method=RequestMethod.POST)
	public String getListadoEliminar(@RequestParam(value="check_value", required=false) List<String> usersRemove) {
		if (usersRemove != null) {
		      for (String id : usersRemove) {
		        this.usersService.deleteUser(Long.valueOf(Long.parseLong(id)));
		      }
		    }
		return "redirect:/user/delete";
	}
	
	@RequestMapping(value = "/user/add")
	public String getUser(Model model) {
		model.addAttribute("rolesList", rolesService.getRoles());
		return "user/add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String setUser(@ModelAttribute User user) {
		usersService.addUser(user);
		return "redirect:/user/list";
	}

//	@RequestMapping("/user/details/{id}")
//	public String getDetail(Model model, @PathVariable Long id) {
//		model.addAttribute("user", usersService.getUser(id));
//		return "user/details";
//	}

	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/user/list";
	}


//	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
//	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute User user) {
//		user.setId(id);
//		usersService.addUser(user);
//		return "redirect:/user/details/" + id;
//	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
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
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		model.addAttribute("offersList", activeUser.getOffers());
		return "home";
	}
	
	@RequestMapping(value = { "/login_error" }, method = RequestMethod.GET)
	public String login_eror() {
		return "login_error";
	}
	
}
