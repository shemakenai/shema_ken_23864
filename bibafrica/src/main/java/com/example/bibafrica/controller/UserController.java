package com.example.bibafrica.controller;

import com.example.bibafrica.DataTransferObject.UserRegistartionDto;
import com.example.bibafrica.model.User;
import com.example.bibafrica.services.UserInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Lazy
@Controller
@RequestMapping("/registration")
public class UserController {
    private final UserInterface userInterface;
     @Autowired
    public UserController(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @GetMapping
    public String showRegistrationForm(){
        return "signup";
    }


    @ModelAttribute("user")
    public UserRegistartionDto userRegistartionDto(){
        return new UserRegistartionDto();
    }
   @PostMapping
    public String registerUserAccount(@ModelAttribute("user")UserRegistartionDto dto){
        User foundUser=userInterface.findByEmail(dto.getEmail());
        if (foundUser!=null &&  (foundUser.getEmail().equalsIgnoreCase(dto.getEmail()))) {
               return "redirect:/registration?error";   
        }
        userInterface.saveAccount(dto);
        return "redirect:/registration?success";
    }
}
