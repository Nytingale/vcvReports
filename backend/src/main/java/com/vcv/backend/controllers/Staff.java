package com.vcv.backend.controllers;

import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.services.UserService;
import com.vcv.backend.utilities.Utils;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/staff")
public class Staff {

    @Autowired
    private UserService userService;

    @PostMapping("/getUsers")
    public List<UserView> getUsers(@RequestBody User vcv) {
        try {
            User validVcv = (User) Utils.isValidEntity(vcv);
            if(validVcv != null) {
                return userService.getUsers(validVcv);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/changeAdmin")
    public MessageView.UserReport changeAdmin(@RequestBody User vcv,
                                              @RequestBody User employee) {
        try {
            User validVcv = (User) Utils.isValidEntity(vcv);
            User validEmployee = (User) Utils.isValidEntity(employee);
            if(validVcv != null && validEmployee != null) {
                return userService.changeAdmin(validVcv, validEmployee);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/searchForUser")
    public UserView searchForUser(@RequestBody User vcv,
                                  @RequestParam(value = "email", required = false) String email,
                                  @RequestParam(value = "company", required = false) String company) {
        try {
            User validVcv = (User) Utils.isValidEntity(vcv);
            String validEmail = Utils.isValidEmail(email);
            String validCompany = Utils.isValidString(company);
            if(validVcv != null && validEmail != null && validCompany != null) {
                return userService.searchForUser(validVcv, validEmail, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/searchForCompany")
    public UserView.CompanyView searchForCompany(@RequestBody User vcv,
                                                 @RequestParam(value = "company", required = false) String company) {
        try {
            User validVcv = (User) Utils.isValidEntity(vcv);
            String validCompany = Utils.isValidString(company);
            if(validVcv != null && validCompany != null) {
                return userService.searchForCompany(validVcv, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/registerCompany")
    public MessageView.CompanyReport registerCompany(@RequestBody User vcv,
                                                     @RequestBody User company) {
        try {
            User validVcv = (User) Utils.isValidEntity(vcv);
            User validCompany = (User) Utils.isValidEntity(company);
            if(validVcv != null && validCompany != null) {
                return userService.registerCompany(validVcv, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/approveCompany")
    public MessageView.CompanyReport approveCompany(@RequestBody User vcv,
                                                    @RequestParam(value = "company", required = false) String company) {
        try {
            User validVcv = (User) Utils.isValidEntity(vcv);
            String validCompany = Utils.isValidString(company);
            if(validVcv != null && validCompany != null) {
                return userService.approveCompany(validVcv, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/blacklistCompany")
    public MessageView.CompanyReport blacklistCompany(@RequestBody User vcv,
                                                      @RequestParam(value = "company", required = false) String company) {
        try {
            User validVcv = (User) Utils.isValidEntity(vcv);
            String validCompany = Utils.isValidString(company);
            if(validVcv != null && validCompany != null) {
                return userService.blacklistCompany(validVcv, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
