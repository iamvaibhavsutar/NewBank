/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.project.NewBank.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.NewBank.model.User;
import com.project.NewBank.repository.UserRepository;

@Service
public class userDetailsService implements UserDetailsService{
    @Autowired
        UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        

        User user = userRepo.findByusername(userName);

        if(user == null){
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
        }
        return null;
    }
}
