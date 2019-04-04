package com.vcv.backend.controllers;

import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.services.JobService;
import com.vcv.backend.services.UserService;
import com.vcv.backend.utilities.Utils;
import com.vcv.backend.views.JobView;
import com.vcv.backend.views.MessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/mechanic")
public class Mechanic {
    @Autowired private JobService jobService;
    @Autowired private UserService userService;

    @GetMapping("/getMechanicJobs")
    public List<JobView> getMechanicJobs(@RequestParam(value = "garage", required = false) String garage) {
        try {
            String validGarage = Utils.isValidString(garage);
            if(validGarage != null) {
                return jobService.getMechanicJobs(validGarage);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/addJob")
    public MessageView.JobReport addJob(@RequestBody Job job) {
        try {
            Job validJob = (Job) Utils.isValidEntity(job);
            if(validJob != null) {
                return jobService.addJob(job);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/updateJob")
    public MessageView.JobReport updateJob(@RequestBody Job job) {
        try {
            Job validJob = (Job) Utils.isValidEntity(job);
            if(validJob != null) {
                return jobService.updateJob(job);
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
