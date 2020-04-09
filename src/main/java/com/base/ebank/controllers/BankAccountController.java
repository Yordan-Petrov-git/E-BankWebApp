package com.base.ebank.controllers;

import com.base.ebank.bindingModels.BankAccountBindingModel;
import com.base.ebank.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/accounts")
public class BankAccountController {
      private  final BankAccountService bankAccountService;
@Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

        @GetMapping("/create")
        @PreAuthorize("isAuthenticated()")
        public String createBankAccount(Model model, @ModelAttribute("bankAccountBindingModel")BankAccountBindingModel bankAccountBindingModel, Principal principal){
    bankAccountBindingModel.setUsername(principal.getName());
    model.addAttribute("view","accounts/create-account");
    model.addAttribute("bankAccountBindingModel",bankAccountBindingModel);
    return "fragments/layout";
        }


    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createBankAccountConfirm(Model model, @ModelAttribute("bankAccountBindingModel")BankAccountBindingModel bankAccountBindingModel){

    if(!this.bankAccountService.createAccount(bankAccountBindingModel)){

        model.addAttribute("view","accounts/create-account");
        model.addAttribute("bankAccountBindingModel",bankAccountBindingModel);
        return "fragments/layout";
    }

        return "redirect:/home";
    }
}
