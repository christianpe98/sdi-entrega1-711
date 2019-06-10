package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.service.UsersService;

@Controller
public class AdminController {

	@Autowired
	private UsersService usersService;

	private Logger logger = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping("/admin/list")
	public String getListUsers(Model model, Principal principal) {
		List<User> users = usersService.getUsers();
		model.addAttribute("user", usersService.usuarioActual());
		model.addAttribute("usersList", users);
		return "admin/list";
	}

	@RequestMapping("/admin/delete")
	public String getDeleteUsers(Model model, Principal principal) {
		List<User> users = usersService.getUsers();
		model.addAttribute("usersList", users);
		model.addAttribute("user", usersService.usuarioActual());
		return "admin/delete";
	}

	@RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
	public String getDeleteUsers(@RequestParam(value = "check_value", required = false) List<String> usersRemove,
			Principal principal) {
		if (usersRemove != null) {
			for (String id : usersRemove) {
				User usuario = usersService.getUser(Long.valueOf(Long.parseLong(id)));
				usersService.deleteUser(Long.valueOf(Long.parseLong(id)));
				logger.info(
						"El administrador " + principal.getName() + "ha eliminado al usuario " + usuario.getEmail());
			}
		}

		return "redirect:/admin/delete";
	}
}
