package com.vcv.backend.controllers;

import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.services.*;
import com.vcv.backend.utilities.Utils;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class Admin {
    @Autowired private FileService fileService;
    @Autowired private UserService userService;

    @PostMapping("/getEmployees")
    public List<UserView> getEmployees(@RequestBody User admin) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            if(validAdmin != null) {
                return userService.getEmployees(validAdmin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/renewSubscription")
    public UserView.SubscriptionConsole rewnewSubscription(@RequestBody User admin) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            if(validAdmin != null) {
                return userService.renewSubscription(validAdmin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/cancelSubscription")
    public UserView.SubscriptionConsole cancelSubscription(@RequestBody User admin) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            if(validAdmin != null) {
                return userService.cancelSubscription(validAdmin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/resetPassword")
    public MessageView resetPassword(@RequestBody User admin,
                                     @RequestParam(value = "email", required = false) String email) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            String validEmail = Utils.isValidEmail(email);
            if(validAdmin!= null && validEmail != null) {
                return userService.resetPassword(validAdmin, validEmail);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/addEmployee")
    public MessageView.UserReport addEmployee(@RequestBody User admin,
                                              @RequestBody User employee) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            User validEmployee = (User) Utils.isValidEntity(employee);
            if(validAdmin != null && validEmployee != null) {
                return userService.addEmployee(validAdmin, validEmployee);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/removeEmployee")
    public MessageView.UserReport removeEmployee(@RequestBody User admin,
                                                 @RequestParam(value = "email", required = false) String email) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            String validEmail = Utils.isValidEmail(email);
            if(validAdmin!= null && validEmail != null) {
                return userService.removeEmployee(validAdmin, validEmail);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/updateWebsite")
    public MessageView.CompanyReport updateWebsite(@RequestBody User admin,
                                                   @RequestParam(value = "website", required = false) String website) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            String validWebsite = Utils.isValidString(website);
            if(validAdmin!= null && validWebsite != null) {
                return userService.updateWebsite(validAdmin, validWebsite);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/uploadImage")
    public MessageView.FileUpload uploadImage(HttpServletRequest request,
                                              @RequestBody User admin,
                                              @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            MultipartFile validImage = Utils.isValidImage(image);
            if(validAdmin!= null && validImage != null) {
                return fileService.uploadImage(validAdmin, validImage, request);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
