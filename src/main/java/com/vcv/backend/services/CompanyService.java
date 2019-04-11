package com.vcv.backend.services;

import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.CompanyServiceException;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.utilities.Utils;
import com.vcv.backend.views.CompanyView;
import com.vcv.backend.entities.Company;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.views.MessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;

    public List<CompanyView> getCompanies() throws CompanyServiceException {
        List<Company> companies = (List<Company>) companyRepository.findAll();
        if(!companies.isEmpty()) {
            return new CompanyView().build(companies);
        } else throw new CompanyServiceException("Error 800: getCompanies() returned null");
    }

    public CompanyView searchForCompany(User vcv,
                                        String company) throws CompanyServiceException {
        // First, Confirm that the Admin is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new CompanyServiceException("Error 820: searchForCompany(vcv, company) has failed you for VCV Staff Authentication");

        // Second, Find the Company to Return
        Company companyDB = companyRepository.findByCompanyName(company);
        if(companyDB != null) {
            return new CompanyView().build(companyDB);
        } else throw new CompanyServiceException("Error 800: searchForCompany(vcv, company) returned null");
    }

    public MessageView.CompanyReport registerCompany(User vcv,
                                                     User admin,
                                                     Company company) throws CompanyServiceException {
        // First, Confirm that the Admin is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new CompanyServiceException("Error 820: registerCompany(vcv, company) has failed you for VCV Staff Authentication");

        // Second, Confirm that no Company with this Name
        Company companyDB = companyRepository.findByCompanyName(company.getCompanyName());
        if(companyDB != null) throw new CompanyServiceException("Error 805: registerCompany(vcv, company) has found an already-existing Company with this Name");

        try {
            // Third, Add the Admin and Company to the Database, with the Admin setup as an Admin
            admin.setRoleId(2L);
            company.setAdmin(admin.getEmail());
            companyRepository.save(company);
            userRepository.save(admin);

            return new MessageView.CompanyReport().build(company, "Successfully Created Company");
        } catch(Exception e) {
            e.printStackTrace();
            throw new CompanyServiceException("Error 815: registerCompany(vcv, company) has failed to register the Company");
        }
    }

    public MessageView.CompanyReport approveCompany(User vcv,
                                                    String company) throws CompanyServiceException {
        // First, Confirm that the Admin is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new CompanyServiceException("Error 820: approveCompany(vcv, company) has failed you for VCV Staff Authentication");

        // Second, Confirm that the Company Exists
        Company companyDB = companyRepository.findByCompanyName(company);
        if(companyDB == null) throw new CompanyServiceException("Error 805: approveCompany(vcv, company) has failed to find such an existing Company");

        // Third, Change the Company to be Approved
        companyDB.setBlacklisted(false);

        try {
            // Fourth, Save the Updated Company to the Database
            companyRepository.save(companyDB);
            return new MessageView.CompanyReport().build(companyDB, "Successfully Approved Company");
        } catch(Exception e) {
            e.printStackTrace();
            throw new CompanyServiceException("Error 815: blacklistCompany(vcv, company) has failed to update the Company");
        }
    }

    public MessageView.CompanyReport blacklistCompany(User vcv,
                                                      String company) throws CompanyServiceException {
        // First, Confirm that the Admin is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new CompanyServiceException("Error 820: blacklistCompany(vcv, company) has failed you for VCV Staff Authentication");

        // Second, Confirm that the Company Exists
        Company companyDB = companyRepository.findByCompanyName(company);
        if(companyDB == null) throw new CompanyServiceException("Error 805: blacklistCompany(vcv, company) has failed to find such an existing Company");

        // Third, Change the Company to be Blacklisted
        companyDB.setBlacklisted(true);

        try {
            // Fourth, Save the Updated Company to the Database
            companyRepository.save(companyDB);
            return new MessageView.CompanyReport().build(companyDB, "Successfully Blacklisted Company");
        } catch(Exception e) {
            e.printStackTrace();
            throw new CompanyServiceException("Error 815: blacklistCompany(vcv, company) has failed to update the Company");
        }
    }

    public MessageView.CompanyReport updateWebsite(User admin,
                                                   String website) throws CompanyServiceException {
        // First, Confirm that the Admin is an Admin or VCV Staff
        if(!Utils.isValidStaffOrAdmin(admin)) {
            throw new CompanyServiceException("Error 805: updateWebsite(admin, website) has failed to identify the Admin as a Company Admin or VCV Staff");
        }

        try {
            // Second, Update the Website in the Database
            Company company = companyRepository.findById(admin.getCompanyId()).get();
            company.setWebsite(website);
            companyRepository.save(company);
            return new MessageView.CompanyReport().build(company,"Successfully Updated Company Website");
        } catch(Exception e) {
            e.printStackTrace();
            throw new CompanyServiceException("Error 815: updateWebsite(admin, website) has failed to update the Company website");
        }
    }

    public MessageView.CompanyReport renewSubscription(User admin) throws CompanyServiceException {
        // First, Confirm that the Admin is the Admin and they are Not Blacklisted
        Optional<Company> company = companyRepository.findById(admin.getCompanyId());
        if(company.isPresent()) {
            if (!Utils.isValidStaffOrAdmin(admin)) {
                throw new CompanyServiceException("Error 805: renewSubscription(admin) has failed to identify the Admin as a Company Admin or VCV Staff");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (company.get().getBlacklisted()) {
                throw new CompanyServiceException("Error 810: renewSubscription(admin) has failed you for Company Approved Authentication");
            }
        } else throw new CompanyServiceException("Error 800: renewSubscription(admin) has returned null");

        // Third, Renew the Company's Subscription Start and End Date based on the Current Date
        Timestamp start = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        Timestamp end = Timestamp.valueOf(LocalDate.now().atStartOfDay().plusYears(1));

        // Fourth, Update the Company Subscription
        company.get().setSubscriptionStartDate(start);
        company.get().setSubscriptionEndDate(end);
        company.get().setValid(Boolean.TRUE);

        try {
            companyRepository.save(company.get());
            return new MessageView.CompanyReport().build(
                    company.get(),
                    "Successfully Renewed Subscription");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CompanyServiceException("Error 815: renewSubscription(admin) failed to renew the Subscription");
        }
    }

    public MessageView.CompanyReport cancelSubscription(User admin) throws CompanyServiceException {
        Optional<Company> company = companyRepository.findById(admin.getCompanyId());
        if(company.isPresent()) {
            if (!Utils.isValidStaffOrAdmin(admin)) {
                throw new CompanyServiceException("Error 805: cancelSubscription(admin) has failed to identify the Admin as a Company Admin or VCV Staff");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (company.get().getBlacklisted()) {
                throw new CompanyServiceException("Error 810: cancelSubscription(admin) has failed you for Company Approved Authentication");
            }
        } else throw new CompanyServiceException("Error 800: cancelSubscription(admin) has returned null");

        // Third, Update the Company Subscription to be Cancelled
        company.get().setSubscriptionEndDate(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
        company.get().setValid(false);

        try {
            companyRepository.save(company.get());
            return new MessageView.CompanyReport().build(
                    company.get(),
                    "Successfully Cancelled Subscription");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CompanyServiceException("Error 815: cancelSubscription(admin) failed to cancel the Subscription");
        }
    }
}
