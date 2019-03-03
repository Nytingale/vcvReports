package com.vcv.backend.services;

import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.UserServiceException;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserView renewSubscription(String email,
                                      String name) throws UserServiceException {
        // First, Confirm that the Email is the Admin
        User user = userRepository.findByEmailAndCompanyName(email, name);
        if(!user.isAdmin()) {
            throw new UserServiceException("Error 405: renewSubscription(name, email) has failed User " + email + " of " + name + " during the" +
                    "Admin Validation Procedure");
        }

        // Second, Confirm that the Company is not Blacklisted
        if(user.isBlackisted()) {
            throw new UserServiceException("Error 410: renewSubscription(name, email) has failed User " + email + " of " + name + " during the" +
                    "Blacklist Validation Procedure");
        }

        // Third, Renew the Company's Subscription Start and End Date based on the Current Date
        Timestamp start = new Timestamp(LocalDate.now().toEpochDay());
        Timestamp end = new Timestamp(LocalDate.now().withYear(1).toEpochDay());

        // Fourth, Update All Employee Users of the Company
        List<User> employees = userRepository.findByCompanyName(name);
        for(User employee: employees) {
            employee.setSubscriptionStartDate(start);
            employee.setSubscriptionEndDate(end);
            employee.setBlacklisted(Boolean.FALSE);
            employee.setValid(Boolean.TRUE);
        }

        userRepository.saveAll(employees);
        return new UserView().build(user);
    }
}
