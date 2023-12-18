package com.example.bibafrica.services;
import com.example.bibafrica.DataTransferObject.UserRegistartionDto;
import com.example.bibafrica.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserInterface extends UserDetailsService {
    public User saveAccount(UserRegistartionDto userRegistartionDto);
    public User findByEmail(String email);
}
