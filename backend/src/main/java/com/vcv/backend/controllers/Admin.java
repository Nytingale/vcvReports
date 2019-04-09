package com.vcv.backend.controllers;

import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.services.*;
import com.vcv.backend.utilities.RequestWrapper;
import com.vcv.backend.utilities.Utils;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.json.JSONObject;
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
    @Autowired private CompanyService companyService;

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
    public MessageView.CompanyReport rewnewSubscription(@RequestBody User admin) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            if(validAdmin != null) {
                return companyService.renewSubscription(validAdmin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/cancelSubscription")
    public MessageView.CompanyReport cancelSubscription(@RequestBody User admin) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            if(validAdmin != null) {
                return companyService.cancelSubscription(validAdmin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/resetPassword")
    public MessageView resetPassword(@RequestBody RequestWrapper.Admin map) {
        try {
            User validAdmin = (User) Utils.isValidEntity(map.getAdmin());
            String validEmail = Utils.isValidEmail(map.getDetails());
            if(validAdmin!= null && validEmail != null) {
                return userService.resetPassword(validAdmin, validEmail);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/changeAdmin")
    public MessageView.UserReport changeAdmin(@RequestBody RequestWrapper.Employee map) {
        try {
            User validAdmin = (User) Utils.isValidEntity(map.getAdmin());
            User validEmployee = (User) Utils.isValidEntity(map.getEmployee());
            if(validAdmin != null && validEmployee != null) {
                return userService.changeAdmin(validAdmin, validEmployee);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/uploadImage")
    public MessageView.FileUpload uploadImage(HttpServletRequest request,
                                              @RequestBody RequestWrapper.Image map) {
        try {
            User validAdmin = (User) Utils.isValidEntity(map.getAdmin());
            MultipartFile validImage = Utils.isValidImage(map.getImage());
            if(validAdmin!= null && validImage != null) {
                return fileService.uploadImage(validAdmin, validImage, request);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/addEmployee")
    public MessageView.UserReport addEmployee(@RequestBody RequestWrapper.Employee map) {
        try {
            User validAdmin = (User) Utils.isValidEntity(map.getAdmin());
            User validEmployee = (User) Utils.isValidEntity(map.getEmployee());
            if(validAdmin != null && validEmployee != null) {
                return userService.addEmployee(validAdmin, validEmployee);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/removeEmployee")
    public MessageView.UserReport removeEmployee(@RequestBody RequestWrapper.Admin map) {
        try {
            User validAdmin = (User) Utils.isValidEntity(map.getAdmin());
            String validEmail = Utils.isValidEmail(map.getDetails());
            if(validAdmin!= null && validEmail != null) {
                return userService.removeEmployee(validAdmin, validEmail);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/updateWebsite")
    public MessageView.CompanyReport updateWebsite(@RequestBody RequestWrapper.Admin map) {
        try {
            User validAdmin = (User) Utils.isValidEntity(map.getAdmin());
            String validWebsite = Utils.isValidString(map.getDetails());
            if(validAdmin!= null && validWebsite != null) {
                return companyService.updateWebsite(validAdmin, validWebsite);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
