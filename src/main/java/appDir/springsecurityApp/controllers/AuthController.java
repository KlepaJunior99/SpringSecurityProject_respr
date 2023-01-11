package appDir.springsecurityApp.controllers;

import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.service.UserServiceImpl;
import appDir.springsecurityApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
	private final PersonValidator personValidator;
	private final UserServiceImpl userService;
	@Autowired
	public AuthController(PersonValidator personValidator, UserServiceImpl userService) {
		this.personValidator = personValidator;
		this.userService = userService;
	}

	@GetMapping(value = "/login")
	public String loginPage() {
		return "auth/login";
	}

	@GetMapping("/registration")
	public String registrationPage(@ModelAttribute("person") Person person) {
		return "auth/registration";
	}

	@PostMapping("/registration")
	public String performRegistration(@ModelAttribute("person") @Valid Person person) {
		userService.register(person);
			return "redirect:/auth/login";
	}
}
