package com.everythingin.user.controller;

import com.everythingin.user.dto.SuccessOutput;
import com.everythingin.user.dto.UserDetails;
import com.everythingin.user.entity.User;
import com.everythingin.user.request.UserRequest;
import com.everythingin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getAllUsers")
    public List<UserDetails> getAllUsers() throws Exception{
        try{
            return userService.allUserDetails();
        }
        catch(Exception e){
            throw e;
        }
    }

    @PostMapping("/saveUser")
    public SuccessOutput saveUser(@RequestBody UserRequest userRequest) throws Exception{
        try {
            SuccessOutput response = new SuccessOutput();
            User dataTobeSaved = new User();
            if(userRequest != null){
                dataTobeSaved.setFirstName(userRequest.getFirstName());
                dataTobeSaved.setLastName(userRequest.getLastName());
                dataTobeSaved.setAge(userRequest.getAge());
                dataTobeSaved.setGmail(userRequest.getGmail());
                dataTobeSaved.setDob(userRequest.getDob());

                userService.saveUserData(dataTobeSaved);

                response.setMessage("Success");
                response.setResponseCode("200");
            }

            else{
                response.setMessage("Request Error");
                response.setMessage("400");
            }
            return response;


        }catch(Exception e){
            System.out.println(e);
            throw e;
        }
    }

    @GetMapping("/userDetailsByMail")
    public UserDetails getUserDataByMail(@RequestParam String mail) throws Exception{
        try{
            UserDetails response = new UserDetails();
            if(mail != null && ! mail.isEmpty()){
                response = userService.getUserDataByMailMethod(mail);

            }
            return response;
        }
        catch(Exception e){
            throw e;
        }
    }







}
