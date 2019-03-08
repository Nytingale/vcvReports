package com.vcv.backend.controllers;

import com.vcv.backend.entities.*;
import com.vcv.backend.enums.CompanyType;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.views.*;
import com.vcv.backend.services.*;
import com.vcv.backend.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired private JobService     jobService;
    @Autowired private UserService    userService;
    @Autowired private ClaimService   claimService;
    @Autowired private PolicyService  policyService;
    @Autowired private VehicleService vehicleService;

    /* Casual Users */
    @GetMapping("/searchForVehicle")
    public VehicleView.BasicReport searchForVehicle(@RequestParam(value = "vin", required = false) String vin,
                                                    @RequestParam(value = "year", required = false) String year,
                                                    @RequestParam(value = "make", required = false) String make,
                                                    @RequestParam(value = "model", required = false) String model) {
        try {
            Integer validYear = Utils.isValidYear(year);
            String validVin = Utils.isValidVin(vin);
            String validMake = Utils.isValidString(make);
            String validModel = Utils.isValidString(model);

            if(validVin != null && validYear == null && validMake == null && validModel == null) {
                return new VehicleView.BasicReport().build(vehicleService.getVehicle(validVin));
            } else if(validVin == null && validYear != null && validMake != null && validModel != null) {
                return new VehicleView.BasicReport().build(vehicleService.getVehicle(validYear, make, model));
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/generateReport")
    public VehicleView.FullReport generateReport(@RequestParam(value = "vin", required = false) String vin) {
        try {
            String validVin = Utils.isValidVin(vin);

            if(validVin != null) {
                VehicleView vehicle = vehicleService.getVehicle(validVin);
                List<ClaimView> claims = claimService.getClaims(validVin);
                List<JobView> jobs = jobService.getJobs(validVin);
                return new VehicleView
                        .FullReport()
                        .build(vehicle, claims, jobs);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /* Company Users */
    /* Admin */
    @GetMapping("/renewSubscription")
    public UserView.SubscriptionConsole rewnewSubscription(@RequestParam(value = "type", required = false) String type,
                                                           @RequestParam(value = "email", required = false) String email,
                                                           @RequestParam(value = "company", required = false) String company) {
        try {
            String validEmail = Utils.isValidEmail(email);
            String validCompany = Utils.isValidSubscribingCompany(company, type);
            if(validEmail != null && validCompany != null) {
                return userService.renewSubscription(validEmail, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/cancelSubscription")
    public UserView.SubscriptionConsole cancelSubscription(@RequestParam(value = "type", required = false) String type,
                                                           @RequestParam(value = "email", required = false) String email,
                                                           @RequestParam(value = "company", required = false) String company) {
        try {
            String validEmail = Utils.isValidEmail(email);
            String validCompany = Utils.isValidSubscribingCompany(company, type);
            if(validEmail != null && validCompany != null) {
                return userService.cancelSubscription(validEmail, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/resetPassword")
    public MessageView resetPassword(@RequestBody User user,
                                     @RequestParam(value = "email", required = false) String email) {
        try {
            User validUser = (User) Utils.isValidEntity(user);
            String validEmail = Utils.isValidEmail(email);
            if(validEmail != null) {
                return userService.resetPassword(validUser, validEmail);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /* Dealership */
    @PostMapping("/registerVehicle")
    public MessageView.Registration registerVehicle(@RequestBody Vehicle vehicle) {
        try {
            Vehicle validVehicle = (Vehicle) Utils.isValidEntity(vehicle);
            if(validVehicle != null) {
                return vehicleService.register(validVehicle);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /* Insurance */
    @PostMapping("/addPolicy")
    public MessageView.InsuranceReport addPolicy(@RequestBody Policy policy) {
        try {
            Policy validPolicy = (Policy) Utils.isValidEntity(policy);
            if(validPolicy != null) {
                return policyService.addPolicy(validPolicy);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/addClaim")
    public MessageView.InsuranceReport addClaim(@RequestBody Claim claim) {
        try {
            Claim validClaim = (Claim) Utils.isValidEntity(claim);
            if(validClaim != null) {
                return claimService.addClaim(validClaim);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/updateClaim")
    public MessageView.InsuranceReport updateClaim(@RequestBody Claim claim) {
        try {
            Claim validClaim = (Claim) Utils.isValidEntity(claim);
            if (validClaim != null) {
                return claimService.updateClaim(validClaim);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/reportStolen")
    public MessageView.StolenReport reportStolen(@RequestParam(value = "vin", required = false) String vin) {
        try {
            String validVin = Utils.isValidVin(vin);
            if(validVin != null) {
                return vehicleService.reportStolen(validVin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/reportAccident")
    public MessageView.AccidentReport reportAccident(@RequestParam(value = "vin", required = false) String vin) {
        try {
            String validVin = Utils.isValidVin(vin);
            if(validVin != null) {
                return vehicleService.reportAccident(validVin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/reportRecovered")
    public MessageView.StolenReport reportRecovered(@RequestParam(value = "vin", required = false) String vin) {
        try {
            String validVin = Utils.isValidVin(vin);
            if(validVin != null) {
                return vehicleService.reportRecovered(validVin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/reportWrittenOff")
    public MessageView.WriteOff reportWrittenOff(@RequestParam(value = "vin", required = false) String vin) {
        try {
            String validVin = Utils.isValidVin(vin);
            if(validVin != null) {
                return vehicleService.reportWrittenOff(validVin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/reportSalvaged")
    public MessageView.SalvageReport reportSalvaged(@RequestParam(value = "vin", required = false) String vin) {
        try {
            String validVin = Utils.isValidVin(vin);
            if(validVin != null) {
                return vehicleService.reportSalvaged(validVin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/linkJobToClaim")
    public MessageView.InsuranceReport linkJobToClaim(@RequestParam(value = "id", required = false) String id,
                                                      @RequestParam(value = "number", required = false) String number,
                                                      @RequestParam(value = "company", required = false) String company) {
        try {
            Long validId = Utils.isValidLong(id);
            String validNumber = Utils.isValidString(number);
            String validCompany = Utils.isValidSubscribingCompany(company, CompanyType.INSURANCE.toString());
            if(validId != null) {
                return claimService.linkJobToClaim(validId, validNumber, validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /* Garage/Mechanic */
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
}
