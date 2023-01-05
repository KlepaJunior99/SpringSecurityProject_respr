package appDir.springsecurityApp.controllers;


import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;
import appDir.springsecurityApp.service.AdminService;
import appDir.springsecurityApp.service.AdminServiceImpl;
import appDir.springsecurityApp.services.RegistrationService;
import appDir.springsecurityApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/users")
public class AdminController {
    private final AdminServiceImpl adminService;
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    @Autowired
    public AdminController(AdminServiceImpl adminService, RegistrationService registrationService, PersonValidator personValidator) {
        this.adminService = adminService;
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("people", adminService.index());
        return "forAdmin/users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", adminService.show(id));
        return "forAdmin/show";
    }
//    @GetMapping("/new")
//        public String createPerson(@ModelAttribute("person") @Valid Person person,
//                BindingResult bindingResult) {
//            personValidator.validate(person, bindingResult);
//            if(bindingResult.hasErrors())
//                return "/auth/registration";
//            registrationService.register(person);
//            return "redirect:/users/admin";
//    }
//    @PostMapping()
//    public String create(@ModelAttribute("person") @Valid Person user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "forAdmin/new";
//        }
//        personService.save(user);
//        return "redirect:/users/admin";
//    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id")int id) {
        model.addAttribute(adminService.show(id));
        return "forAdmin/editUser";
    }
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("person") @Valid Person user, @ModelAttribute("role") @Valid Role role, BindingResult bindingResult, @PathVariable("id")int id) {
        if (bindingResult.hasErrors()) {
            return "forAdmin/editUser";
        }
        adminService.update(user, role, id);
        return "redirect:/user";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id")int id) {
        adminService.delete(id);
        return "redirect:/users/admin";
    }
}