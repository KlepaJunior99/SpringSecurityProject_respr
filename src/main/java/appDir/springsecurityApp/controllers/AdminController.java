package appDir.springsecurityApp.controllers;


import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;
import appDir.springsecurityApp.rep.PeopleRepository;
import appDir.springsecurityApp.service.AdminServiceImpl;
import appDir.springsecurityApp.services.PersonDetailsService;
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
    private final PersonValidator personValidator;

    @Autowired
    public AdminController(AdminServiceImpl adminService, PersonValidator personValidator) {
        this.adminService = adminService;
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

    @GetMapping("/new")
    public String newPage(@ModelAttribute("person") Person person) {
        return "forAdmin/new";
    }

    @PostMapping("/new")
    public String newUser(@ModelAttribute("person") @Valid Person person, @ModelAttribute("person") Role role,
                          BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            return "/forAdmin/new";
        }
        adminService.save(person, role);
        return "redirect:/auth/login";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id")int id) {
        model.addAttribute(adminService.show(id));
        return "forAdmin/editUser";
    }
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, @ModelAttribute("person") Role role) {
        adminService.update(person, role);
        return "redirect:/user";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id")int id) {
        adminService.delete(id);
        return "redirect:/users/admin";
    }
}