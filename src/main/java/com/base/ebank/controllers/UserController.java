package com.base.ebank.controllers;

import com.base.ebank.bindingModels.UserBindingModel;
import com.base.ebank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
   // @PreAuthorize("isAnonymous()")
    public String login(Model model, @ModelAttribute("userBindingModel") UserBindingModel userBindingModel) {
        model.addAttribute("view", "users/login-user");
        model.addAttribute("userBindingModel", userBindingModel);
        return "fragments/layout";
    }

    @GetMapping("/register")
   // @PreAuthorize("isAnonymous()")
    public String register(Model model, @ModelAttribute("userBindingModel") UserBindingModel userBindingModel) {
        model.addAttribute("view", "users/register-user");
        model.addAttribute("userBindingModel", userBindingModel);
        return "fragments/layout";
    }

    @PostMapping("/register")
   // @PreAuthorize("isAnonymous()")
    public String registerConfirm(Model model, @ModelAttribute("userBindingModel") UserBindingModel userBindingModel){

    if (!this.userService.registerUser(userBindingModel)){
        model.addAttribute("view", "users/register-user");
        model.addAttribute("userBindingModel", userBindingModel);
        return "fragments/layout";
    }
      return  "redirect:/login";
    }

}
