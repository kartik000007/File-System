package com.Email.MailSending.service;

import com.Email.MailSending.dto.RegistrationDTO;
import com.Email.MailSending.dto.UserSearchDTO;
import com.Email.MailSending.module.Users;
import com.Email.MailSending.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.Email.MailSending.dto.LoginDTO;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MessageService messageService;

    @Autowired
    public UserService(UserRepository userRepository, MessageService messageService) {
        this.userRepository = userRepository;
        this.messageService = messageService;
    }

    public void registerUser(RegistrationDTO registrationDTO) {

        Users user = new Users();
        user.setName(registrationDTO.getUsername());
        user.setPassword(registrationDTO.getPassword());
        user.setEmail(registrationDTO.getEmail());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        userRepository.save(user);
    }

    public boolean loginUser(LoginDTO loginDTO) {

        Users user = userRepository.findByName(loginDTO.getUsername());
        if (user != null && user.getPassword().equals(loginDTO.getPassword())) {
            messageService.sendMessage(user.getPhoneNumber(), "Login successful!");
            return true;
        }
        return false;
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email:- " + email));
    }

    public Page<UserSearchDTO> getUsers(String name, int page, int size, String sortField, String sortDirection) {
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page,size,sort);
        return userRepository.findByNameContainingIgnoreCase(name,pageable).map(
                users -> new UserSearchDTO(
                        users.getName(),
                        users.getEmail(),
                        users.getPhoneNumber()
                )
        );
    }

//    public UserSearchDTO convert2DTO (Users user){
//        UserSearchDTO userSearchDTO=new UserSearchDTO();
//        userSearchDTO.setName(user.getName());
//        userSearchDTO.setEmail(user.getEmail());
//        userSearchDTO.setPhoneNumber(user.getPhoneNumber());
//
//        return userSearchDTO;
//    }
}
