package com.vcv.backend.controllers;

import com.vcv.backend.entities.*;
import com.vcv.backend.enums.CompanyType;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.views.*;
import com.vcv.backend.services.*;
import com.vcv.backend.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class Controller {
    @Autowired private JobService     jobService;
    @Autowired private FileService    fileService;
    @Autowired private UserService    userService;
    @Autowired private ClaimService   claimService;
    @Autowired private PolicyService  policyService;
    @Autowired private VehicleService vehicleService;

    /* Casual Users */
    @GetMapping("/getWebsite")
    public UserView.CompanyView getWebsite(@RequestParam(value = "company", required = false) String company) {
        try {
            String validCompany = Utils.isValidString(company);
            if(validCompany != null) {
                return userService.getWebsite(validCompany);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
                Vehicle vehicle = vehicleService.getVehicle(validVin);
                List<Claim> claims = claimService.getClaims(validVin);
                List<Job> jobs = jobService.getJobs(validVin);
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



    /* Dealership */
    @GetMapping("/getRegisteredVehicles")
    public List<VehicleView> getRegisteredVehicles(@RequestParam(value = "dealership", required = false) String dealership) {
        try {
            String validDealership = Utils.isValidString(dealership);
            if(validDealership != null) {
                return vehicleService.getRegisteredVehicles(validDealership);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
    @GetMapping("/getPolicy")
    public PolicyView getPolicy(@RequestParam(value = "vin", required = false) String vin) {
        try {
            String validVin = Utils.isValidVin(vin);
            if(validVin != null) {
                return policyService.getPolicy(validVin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getInsurancePolicies")
    public List<PolicyView> getInsurancePolicies(@RequestParam(value = "insurance", required = false) String insurance) {
        try {
            String validInsurance = Utils.isValidString(insurance);
            if(validInsurance != null) {
                return policyService.getInsurancePolicies(validInsurance);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getInsuredVehicles")
    public List<VehicleView> getInsuredVehicles(@RequestParam(value = "insurance", required = false) String insurance) {
        try {
            String validInsurance = Utils.isValidString(insurance);
            if(validInsurance != null) {
                return vehicleService.getInsuredVehicles(validInsurance);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getInsuranceClaims")
    public List<ClaimView> getInsuranceClaims(@RequestParam(value = "insurance", required = false) String insurance) {
        try {
            String validInsurance = Utils.isValidString(insurance);
            if(validInsurance != null) {
                return claimService.getInsuranceClaims(validInsurance);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    @GetMapping("/reportStolen")
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

    @GetMapping("/reportAccident")
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

    @GetMapping("/reportRecovered")
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

    @GetMapping("/reportWrittenOff")
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

    @GetMapping("/reportSalvaged")
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

    @GetMapping("/linkJobToClaim")
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



    /* VCV Staff */
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
