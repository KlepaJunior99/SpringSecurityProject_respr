package appDir.springsecurityApp.controllers;

import appDir.springsecurityApp.dao.PersonDAO;
import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;
import appDir.springsecurityApp.service.UserServiceImpl;
import appDir.springsecurityApp.services.PersonDetailsService;
import appDir.springsecurityApp.services.RegistrationService;
import appDir.springsecurityApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final UserServiceImpl userService;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public UserController(RegistrationService registrationService, PersonValidator personValidator, PersonDAO userDAO, UserServiceImpl userService, PersonDetailsService personDetailsService) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.userService = userService;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("")
    public String showUserInfo(Principal principal, Model model) {
        model.addAttribute("person", userService.show(((Person) personDetailsService
                .loadUserByUsername(principal.getName())).getId()));
        return "useService/show";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id")int id) {
        model.addAttribute(userService.show(id));
        return "useService/edit";
    }
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, @ModelAttribute("role") @Valid Role role, BindingResult bindingResult, @PathVariable("id")int id) {
        userService.delete(id);
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "useService/show";
        registrationService.register(person);
        return "redirect:/user";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id")int id) {
        userService.delete(id);
        return "redirect:/auth/login";
    }
}
