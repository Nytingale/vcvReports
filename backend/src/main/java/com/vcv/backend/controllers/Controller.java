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

    /* Website */
    @GetMapping("/getPartners")
    public List<UserView.CompanyView> getPartners() {
        try {
            return userService.getPartners();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    @GetMapping("/cancelSubscription")
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

    @PostMapping("/updateWebsite")
    public MessageView.CompanyReport updateWebsite(@RequestBody User admin) {
        try {
            User validAdmin = (User) Utils.isValidEntity(admin);
            if(validAdmin != null) {
                return userService.updateWebsite(validAdmin);
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



    /* VCV Staff */
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
