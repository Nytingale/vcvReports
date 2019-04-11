package com.vcv.backend.controllers;

import com.vcv.utilities.Utils;
import com.vcv.utilities.RequestWrapper;

import com.vcv.backend.entities.User;
import com.vcv.backend.entities.Company;
import com.vcv.backend.services.UserService;
import com.vcv.backend.services.CompanyService;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.views.UserView;
import com.vcv.backend.views.CompanyView;
import com.vcv.backend.views.MessageView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public MessageView.UserReport changeAdmin(@RequestBody RequestWrapper.Employee map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.getAdmin());
            User validEmployee = (User) Utils.isValidEntity(map.getEmployee());
            if(validVcv != null && validEmployee != null) {
                return userService.changeAdmin(validVcv, validEmployee);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/searchForUser")
    public UserView searchForUser(@RequestBody RequestWrapper.Admin map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.getAdmin());
            String validClient = Utils.isValidEmail(map.getDetails());
            if(validVcv != null && validClient != null) {
                return userService.searchForUser(validVcv, validClient);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/searchForCompany")
    public CompanyView searchForCompany(@RequestBody RequestWrapper.Admin map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.getAdmin());
            String validCompany = Utils.isValidString(map.getDetails());
            if(validVcv != null && validCompany != null) {
                return companyService.searchForCompany(validVcv, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/resetPassword")
    public MessageView resetPassword(@RequestBody RequestWrapper.Admin map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.getAdmin());
            String validEmail = Utils.isValidEmail(map.getDetails());
            if(validVcv != null && validEmail != null) {
                return userService.resetPassword(validVcv, validEmail);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/addEmployee")
    public MessageView.UserReport addEmployee(@RequestBody RequestWrapper.Employee map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.getAdmin());
            User validEmployee = (User) Utils.isValidEntity(map.getEmployee());
            if(validVcv != null && validEmployee != null) {
                return userService.addEmployee(validVcv, validEmployee);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/removeEmployee")
    public MessageView.UserReport removeEmployee(@RequestBody RequestWrapper.Admin map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.getAdmin());
            String validEmail = Utils.isValidEmail(map.getDetails());
            if(validVcv!= null && validEmail != null) {
                return userService.removeEmployee(validVcv, validEmail);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/registerCompany")
    public MessageView.CompanyReport registerCompany(@RequestBody RequestWrapper.Registration map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.getVcv());
            User validAdmin = (User) Utils.isValidEntity(map.getAdmin());
            Company validCompany = (Company) Utils.isValidEntity(map.getCompany());
            if(validVcv != null && validAdmin != null && validCompany != null) {
                return companyService.registerCompany(validVcv, validAdmin, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/approveCompany")
    public MessageView.CompanyReport approveCompany(@RequestBody RequestWrapper.Admin map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.getAdmin());
            String validCompany = Utils.isValidString(map.getDetails());
            if(validVcv != null && validCompany != null) {
                return companyService.approveCompany(validVcv, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/blacklistCompany")
    public MessageView.CompanyReport blacklistCompany(@RequestBody RequestWrapper.Admin map) {
        try {
            User validVcv = (User) Utils.isValidEntity(map.getAdmin());
            String validCompany = Utils.isValidString(map.getDetails());
            if(validVcv != null && validCompany != null) {
                return companyService.blacklistCompany(validVcv, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/changePassword")
    public MessageView.UserReport changePassword(@RequestBody RequestWrapper.Admin map) {
        try {
            User validUser = (User) Utils.isValidEntity(map.getAdmin());
            String validNewPassword = Utils.isValidPassword(map.getDetails());
            if(validUser != null && validNewPassword != null) {
                return userService.changePassword(validUser, validNewPassword);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
