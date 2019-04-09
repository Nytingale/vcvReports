package com.vcv.backend.controllers;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Policy;
import com.vcv.backend.entities.User;
import com.vcv.backend.enums.CompanyType;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.services.*;
import com.vcv.backend.utilities.RequestWrapper;
import com.vcv.backend.utilities.Utils;
import com.vcv.backend.views.ClaimView;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.PolicyView;
import com.vcv.backend.views.VehicleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/insurance")
public class Insurance {

    @Autowired private UserService userService;
    @Autowired private ClaimService claimService;
    @Autowired private PolicyService policyService;
    @Autowired private VehicleService vehicleService;

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
    public MessageView.WriteOffReport reportWrittenOff(@RequestParam(value = "vin", required = false) String vin) {
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
            String validCompany = Utils.isValidSubscribingCompany(company, CompanyType.Insurance.toString());
            if(validId != null) {
                return claimService.linkJobToClaim(validId, validNumber, validCompany);
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
