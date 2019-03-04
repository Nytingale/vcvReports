package com.vcv.backend.controllers;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Policy;
import com.vcv.backend.entities.User;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.views.ClaimView;
import com.vcv.backend.views.JobView;
import com.vcv.backend.views.UserView;
import com.vcv.backend.views.VehicleView;
import com.vcv.backend.services.*;
import com.vcv.backend.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
       /* General */
    @GetMapping("/renewSubscription")
    public UserView.SubscriptionConsole rewnewSubscription(@RequestParam(value = "name", required = false) String name,
                                                           @RequestParam(value = "type", required = false) String type,
                                                           @RequestParam(value = "email", required = false) String email) {
        try {
            String validName = Utils.isValidSubscribingCompany(name, type);
            String validEmail = Utils.isValidEmail(email);
            if(validName != null || validEmail != null) {
                UserView user = userService.renewSubscription(validEmail, validName);
                return new UserView
                        .SubscriptionConsole()
                        .build(user);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/cancelSubscription")
    public List<UserView> cancelSubscription(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "type", required = false) String type,
                                      @RequestParam(value = "email", required = false) String email) {
        try {
            String validName = Utils.isValidSubscribingCompany(name, type);
            String validEmail = Utils.isValidEmail(email);
            if(validName != null || validEmail != null) {
                return userService.cancelSubscription(validName, validEmail);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /* Dealership */
    @PostMapping("/registerVehicle")
    public Boolean registerVehicle(@RequestBody Vehicle vehicle,
                                   @RequestParam(value = "dealership", required = false) String dealership) {
        try {
            String validDealership = Utils.isValidString(dealership);
            Vehicle validVehicle = (Vehicle) Utils.isValidEntity(vehicle);
            if(validDealership != null && validVehicle != null) {
                return vehicleService.register(validVehicle, validDealership);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /* Insurance */
    @PostMapping("/addPolicy")
    public Boolean addPolicy(@RequestBody Policy policy,
                             @RequestParam(value = "insurance", required = false) String insurance) {
        try {
            String validInsurance = Utils.isValidString(insurance);
            Policy validPolicy = (Policy) Utils.isValidEntity(policy);
            if(validInsurance != null && validPolicy != null) {
                return policyService.addPolicy(validPolicy, validInsurance);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/addClaim")
    public Boolean addClaim(@RequestBody Claim claim,
                            @RequestParam(value = "insurance", required = false) String insurance) {
        try {
            String validInsurance = Utils.isValidString(insurance);
            Claim validClaim = (Claim) Utils.isValidEntity(claim);
            if (validInsurance != null && validClaim != null) {
                return claimService.addClaim(validClaim, validInsurance);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/updateClaim")
    public Boolean updateClaim(@RequestBody Claim claim) {
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

    @GetMapping("/reportStolen")
    public Boolean reportStolen(@RequestParam(value = "vin", required = false) String vin) {
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

    @GetMapping("/reportRecovered")
    public Boolean reportRecovered(@RequestParam(value = "vin", required = false) String vin) {
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

    @GetMapping("/reportWrittenOff")
    public Boolean reportWrittenOff(@RequestParam(value = "vin", required = false) String vin) {
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

    @GetMapping("/reportSalvaged")
    public Boolean reportSalvaged(@RequestParam(value = "vin", required = false) String vin) {
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
}
