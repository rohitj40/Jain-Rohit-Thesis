package org.example.consumerportal.controller;

import org.example.consumerportal.request.model.RegisterModel;
import org.example.consumerportal.service.RegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

@Controller
public class RegisterController {

	@Autowired
	private RegisterServiceImpl registerService;

	@GetMapping("/register")
	public String getLogin(@ModelAttribute("registerModel") RegisterModel register, Model model) {
		model.addAttribute("register", register);
		return "register";
	}


	@PostMapping("/register")
	public String register(@ModelAttribute("registerModel") RegisterModel register, Model model) {
		String token = register.getFirstname() + register.getSurname() + register.getEmail() + LocalDateTime.now();
		register.setTokenId(register.getFirstname().toUpperCase().substring(0, 2)
				+ Math.abs(token.toUpperCase().hashCode()));
		registerService.saveRegister(register);

		model.addAttribute("register", register);
		return "/register";
	}

}
