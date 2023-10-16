package org.example.consumerportal.controller;

import org.example.consumerportal.request.model.LoginModel;
import org.example.consumerportal.service.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String getLogin(@ModelAttribute("loginModel") LoginModel login, Model model) {
		return "login";
	}

	@GetMapping("/login/{token}")
	public String authenticateByToken(@PathVariable String token, Model model) {
		return "auth/user_home";
	}


	@PostMapping("/login")
	public String authenticate(@ModelAttribute("loginModel") LoginModel login, Model model) {
		return "auth/user_home";
	}

}
