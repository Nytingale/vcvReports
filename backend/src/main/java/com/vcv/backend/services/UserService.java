package com.vcv.backend.services;

import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.UserServiceException;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserView.CompanyView> getPartners(String company) throws UserServiceException {
        // First, Gather the List of Companies
        List<User> companies = userRepository.findByCompanyNameOrderBySubscriptionStartDateDesc(company);
        if(companies.size() > 0) {

            // Second, Remove the Invalid and Blacklisted Companies
            for(User partner: companies) if(!partner.isValid() || partner.isBlackisted()) companies.remove(partner);

            return new UserView.CompanyView().build(new UserView().build(companies));
        } else throw new UserServiceException("Error 500: getCompanies(company) returned null");

    }

    public List<UserView> getCompanyEmployees(String email,
                                              String company) throws UserServiceException {
        // First, Confirm that the Email is the Admin
        User user = userRepository.findByEmailAndCompanyName(email, company);
        if(user != null) {
            if (!user.isAdmin()) {
                throw new UserServiceException("Error 405: getCompanyEmployees(email, company) has failed User " + email + " of " + company + " during the" +
                        "Admin Validation Procedure");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (user.isBlackisted()) {
                throw new UserServiceException("Error 410: getCompanyEmployees(email, company) has failed User " + email + " of " + company + " during the" +
                        "Blacklist Validation Procedure");
            }
        } else throw new UserServiceException("Error 400: getCompanyEmployees(email, company) has returned null");

        List<User> employees = userRepository.findByCompanyNameOrderBySubscriptionStartDateDesc(company);
        if(employees.size() > 0) return new UserView().build(employees);
        else throw new UserServiceException("Error 500: getCompanyVehicles(dealership) returned null");
    }

    public UserView.SubscriptionConsole renewSubscription(String email,
                                                          String company) throws UserServiceException {
        // First, Confirm that the Email is the Admin
        User user = userRepository.findByEmailAndCompanyName(email, company);
        if(user != null) {
            if (!user.isAdmin()) {
                throw new UserServiceException("Error 405: renewSubscription(email, company) has failed User " + email + " of " + company + " during the" +
                        "Admin Validation Procedure");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (user.isBlackisted()) {
                throw new UserServiceException("Error 410: renewSubscription(email, company) has failed User " + email + " of " + company + " during the" +
                        "Blacklist Validation Procedure");
            }
        } else throw new UserServiceException("Error 400: renewSubscription(email, company) has returned null");

        // Third, Renew the Company's Subscription Start and End Date based on the Current Date
        Timestamp start = new Timestamp(LocalDate.now().toEpochDay());
        Timestamp end = new Timestamp(LocalDate.now().withYear(1).toEpochDay());

        // Fourth, Update All Employee Users of the Company
        List<User> employees = userRepository.findByCompanyNameOrderBySubscriptionStartDateDesc(company);
        for(User employee: employees) {
            employee.setSubscriptionStartDate(start);
            employee.setSubscriptionEndDate(end);
            employee.setBlacklisted(Boolean.FALSE);
            employee.setValid(Boolean.TRUE);
        }

        try {
            userRepository.saveAll(employees);
            return new UserView.SubscriptionConsole().build(new UserView().build(user));
        } catch (Exception e) {
            throw new UserServiceException("Error 415: cancelSubscription(email, company) failed to renew the Subscription");
        }
    }

    public UserView.SubscriptionConsole cancelSubscription(String email,
                                                           String company) throws UserServiceException {
        // First, Confirm that the Email is the Admin
        User user = userRepository.findByEmailAndCompanyName(email, company);
        if(user != null) {
            if (!user.isAdmin()) {
                throw new UserServiceException("Error 405: renewSubscription(email, company) has failed User " + email + " of " + company + " during the" +
                        "Admin Validation Procedure");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (user.isBlackisted()) {
                throw new UserServiceException("Error 410: renewSubscription(email, company) has failed User " + email + " of " + company + " during the" +
                        "Blacklist Validation Procedure");
            }
        } else throw new UserServiceException("Error 400: renewSubscription(email, company) has returned null");

        // Third, Update all Employee Users of the Company to have their Subscription Cancelled
        List<User> employees = userRepository.findByCompanyNameOrderBySubscriptionStartDateDesc(company);
        for(User employee: employees) {
            employee.setSubscriptionEndDate(new Timestamp(LocalDate.now().toEpochDay()));
            employee.setValid(false);
        }

        try {
            userRepository.saveAll(employees);
            return new UserView.SubscriptionConsole().build(new UserView().build(user));
        } catch (Exception e) {
            throw new UserServiceException("Error 415: cancelSubscription(email, company) failed to cancel the Subscription");
        }
    }
}
