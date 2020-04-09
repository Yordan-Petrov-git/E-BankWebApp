package com.base.ebank.services;

import com.base.ebank.bindingModels.UserBindingModel;
import com.base.ebank.entities.Role;
import com.base.ebank.entities.User;
import com.base.ebank.repositories.RoleRepository;
import com.base.ebank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = this.userRepository.findByUsername(username);

        if (userDetails == null) {
            throw new UsernameNotFoundException("Invalid user!");
        }

        return userDetails;
    }



    public boolean registerUser(UserBindingModel model){
        User user = this.userRepository.findByUsername(model.getUsername());

        if(user != null  ){
            return false;
        }else if (!model.getPassword().equals(model.getConfirmPassword())){
             return  false;
        }


        Role role = this.roleRepository.findByAuthority(
                this.userRepository.count() == 0 ?
                        "ADMIN":"USER");
        if (role == null) return  false;
        user = new User();
        user.setEmail(model.getEmail());
        user.setPassword(this.bCryptPasswordEncoder.encode(model.getPassword()));
        user.setUsername(model.getUsername());
        user.addRole(role);

        this.userRepository.saveAndFlush(user);
        return true;
    }

    public User findOneByUsername(String username ){
       return this.userRepository.findByUsername(username);
    }

}

