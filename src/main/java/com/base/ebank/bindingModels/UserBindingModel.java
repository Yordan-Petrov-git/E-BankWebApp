package com.base.ebank.bindingModels;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserBindingModel {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public UserBindingModel() {
    }

    @NotNull
    @NotEmpty
    public String getUsername() {
        return this.username;
    }
    @NotNull
    @NotEmpty
    public void setUsername(String username) {
        this.username = username;
    }
    @NotNull
    @NotEmpty
    public String getEmail() {
        return this.email;
    }
    @NotNull
    @NotEmpty
    public void setEmail(String email) {
        this.email = email;
    }
    @NotNull
    @NotEmpty
    public String getPassword() {
        return this.password;
    }
    @NotNull
    @NotEmpty
    public void setPassword(String password) {
        this.password = password;
    }
    @NotNull
    @NotEmpty
    public String getConfirmPassword() {
        return this.confirmPassword;
    }
    @NotNull
    @NotEmpty
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
