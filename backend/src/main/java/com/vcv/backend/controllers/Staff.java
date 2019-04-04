package com.vcv.backend.controllers;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.services.CompanyService;
import com.vcv.backend.services.UserService;
import com.vcv.backend.utilities.Utils;
import com.vcv.backend.views.CompanyView;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/staff")
public class Staff {

    @Autowired private UserService userService;
    @Autowired private CompanyService companyService;

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
    public MessageView.UserReport changeAdmin(@RequestBody Map<String, User> map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.get("VCV"));
            User validEmployee = (User) Utils.isValidEntity(map.get("Employee"));
            if(validVcv != null && validEmployee != null) {
                return userService.changeAdmin(validVcv, validEmployee);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/searchForUser")
    public UserView searchForUser(@RequestBody Map<String, Object> map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.get("VCV"));
            String validClient = Utils.isValidEmail((String) map.get("Client"));
            if(validVcv != null && validClient != null) {
                return userService.searchForUser(validVcv, validClient);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/searchForCompany")
    public CompanyView searchForCompany(@RequestBody Map<String, Object> map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.get("VCV"));
            String validCompany = Utils.isValidString((String) map.get("Company"));
            if(validVcv != null && validCompany != null) {
                return companyService.searchForCompany(validVcv, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/addEmployee")
    public MessageView.UserReport addEmployee(@RequestBody Map<String, User> map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.get("VCV"));
            User validEmployee = (User) Utils.isValidEntity(map.get("Employee"));
            if(validVcv != null && validEmployee != null) {
                return userService.addEmployee(validVcv, validEmployee);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/registerCompany")
    public MessageView.CompanyReport registerCompany(@RequestBody Map<String, Object> map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.get("VCV"));
            User validAdmin = (User) Utils.isValidEntity(map.get("Admin"));
            Company validCompany = (Company) Utils.isValidEntity(map.get("Company"));
            if(validVcv != null && validAdmin != null && validCompany != null) {
                return companyService.registerCompany(validVcv, validAdmin, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/approveCompany")
    public MessageView.CompanyReport approveCompany(@RequestBody Map<String, Object> map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.get("VCV"));
            String validCompany = Utils.isValidString((String) map.get("Company"));
            if(validVcv != null && validCompany != null) {
                return companyService.approveCompany(validVcv, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/blacklistCompany")
    public MessageView.CompanyReport blacklistCompany(@RequestBody Map<String, Object> map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.get("VCV"));
            String validCompany = Utils.isValidString((String) map.get("Company"));
            if(validVcv != null && validCompany != null) {
                return companyService.blacklistCompany(validVcv, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/changePassword")
    public MessageView.UserReport changePassword(@RequestBody Map<String, Object> map) {
        try {
            User validUser = (User) Utils.isValidEntity(map.get("User"));
            String validNewPassword = Utils.isValidPassword((String) map.get("New Password"));
            if(validUser != null && validNewPassword != null) {
                return userService.changePassword(validUser, validNewPassword);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
