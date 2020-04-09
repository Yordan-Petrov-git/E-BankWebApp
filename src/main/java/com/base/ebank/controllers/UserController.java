package com.base.ebank.controllers;

import com.base.ebank.bindingModels.UserBindingModel;
import com.base.ebank.entities.Transaction;
import com.base.ebank.services.TransactionService;
import com.base.ebank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;
    private final TransactionService transactionService;


    @Autowired
    public UserController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String login(Model model, @ModelAttribute("userBindingModel") UserBindingModel userBindingModel) {
        model.addAttribute("view", "users/login-user");
        model.addAttribute("userBindingModel", userBindingModel);
        return "fragments/layout";
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(Model model, @ModelAttribute("userBindingModel") UserBindingModel userBindingModel) {
        model.addAttribute("view", "users/register-user");
        model.addAttribute("userBindingModel", userBindingModel);
        return "fragments/layout";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String registerConfirm(Model model, @ModelAttribute("userBindingModel") UserBindingModel userBindingModel) {

        if (!this.userService.registerUser(userBindingModel)) {
            model.addAttribute("view", "users/register-user");
            model.addAttribute("userBindingModel", userBindingModel);
            return "fragments/layout";
        }
        return "redirect:/login";
    }

    @GetMapping("/transactions")
    //@PreAuthorize("isAnonymous()")
    public String allTransactions(Principal principal, Model model) {

        model.addAttribute("username", principal.getName());
        model.addAttribute("transactions", this.transactionService.getAllByName(principal.getName()));
        model.addAttribute("view", "users/transactions");
        return "fragments/layout";
    }
}
