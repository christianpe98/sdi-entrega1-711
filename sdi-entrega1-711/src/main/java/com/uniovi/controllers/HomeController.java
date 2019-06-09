package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.service.UsersService;

@Controller
public class HomeController {

	@Autowired
	private UsersService userService;
	
	@RequestMapping("/")
	public String index(Model model)
	{
		model.addAttribute("user", userService.usuarioActual());
		return "index";
	}
	
	
}
