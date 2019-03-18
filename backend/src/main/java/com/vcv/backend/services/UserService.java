package com.vcv.backend.services;

import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.UserServiceException;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.utilities.Utils;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    /* Website */
    public UserView.CompanyView getWebsite(String company) throws UserServiceException {
        User companyDB = userRepository.findByCompanyId(company, 1);
        if(companyDB != null) {
            return new UserView.CompanyView().build(companyDB);
        } else throw new UserServiceException("Error 400: getWebsite(company) returned null");
    }

    /* VCV Staff */
    public UserView searchForUser(User vcv,
                                  String email,
                                  String company) throws UserServiceException {
        // First, Confirm that the User is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new UserServiceException("Error 420: searchForUser(vcv, email, company) has failed you for VCV Staff Authentication");

        User user = userRepository.findByEmail(email, company);
        if(user != null) {
            return new UserView().build(user);
        } else throw new UserServiceException("Error 400: searchForUser(vcv, email, company) returned null");
    }

    public UserView.CompanyView searchForCompany(User vcv,
                                                 String company) throws UserServiceException {
        // First, Confirm that the User is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new UserServiceException("Error 420: searchForCompany(vcv, company) has failed you for VCV Staff Authentication");

        // Second, Find the Company to Return
        User companyDB = userRepository.findByCompanyId(company, 1);
        if(companyDB != null) {
            return new UserView.CompanyView().build(companyDB);
        } else throw new UserServiceException("Error 400: searchForCompany(vcv, company) returned null");
    }

    public List<UserView> getUsers(User vcv) throws UserServiceException {
        // First, Confirm that the User is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new UserServiceException("Error 420: getUsers(vcv) has failed you for VCV Staff Authentication");

        // Second, Find the Clients to Return
        List<User> users = (List<User>) userRepository.findAll();
        if(users.isEmpty()) throw new UserServiceException("Error 400: getUsers(vcv) returned null");
        return new UserView().build(users);
    }

    public MessageView.UserReport changeAdmin(User vcv,
                                              User employee) throws UserServiceException {
        // First, Confirm that the User is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new UserServiceException("Error 420: changeAdmin(vcv, employee) has failed you for VCV Staff Authentication");

        // Second, Find the Admin of the Company
        User admin = userRepository.findByCompanyId(employee.getCompanyId(), 1);
        if(admin != null) {

            // Third, Swap the Roles and Website Information of the Employee and Admin
            employee.setWebsite(admin.getWebsite());
            employee.setAdmin(true);
            admin.setWebsite(null);
            admin.setAdmin(false);

            try {
                // Fourth, Update the new records of the Employee and the Admin
                userRepository.save(admin);
                userRepository.save(employee);
                return new MessageView.UserReport().build(employee, "Successfully Changed Company Admins");
            } catch (Exception e) {
                e.printStackTrace();
                throw new UserServiceException("Error 415: changeAdmin(vcv, employee) failed to change the Company Admin");
            }
        } else throw new UserServiceException("Error 400: changeAdmin(vcv, employee) returned null");
    }

    public MessageView.CompanyReport registerCompany(User vcv,
                                                     User company) throws UserServiceException {
        // First, Confirm that the User is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new UserServiceException("Error 420: registerCompany(vcv, company) has failed you for VCV Staff Authentication");

        // Second, Confirm that no Company with this Name
        User companyDB = userRepository.findByCompanyId(company.getCompanyId(), 1);
        if(companyDB != null) throw new UserServiceException("Error 405: registerCompany(vcv, company) has found an already-existing Company with this Name");

        try {
            // Third, Add the User and Company to the Database, with the User setup as an Admin
            company.setAdmin(true);
            userRepository.save(company);

            return new MessageView.CompanyReport().build(company, "Successfully Created Company");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: registerCompany(vcv, company) has failed to register the Company");
        }
    }

    public MessageView.CompanyReport approveCompany(User vcv,
                                                    String company) throws UserServiceException {
        // First, Confirm that the User is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new UserServiceException("Error 420: approveCompany(vcv, company) has failed you for VCV Staff Authentication");

        // Second, Confirm that the Company Exists
        User companyDB = userRepository.findByCompanyId(company, 1);
        if(companyDB == null) throw new UserServiceException("Error 405: approveCompany(vcv, company) has failed to find such an existing Company");

        // Third, Change the Company to be Approved
        companyDB.setBlacklisted(false);

        try {
            // Fourth, Save the Updated Company to the Database
            userRepository.save(companyDB);
            return new MessageView.CompanyReport().build(companyDB, "Successfully Approved Company");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: blacklistCompany(vcv, company) has failed to update the Company");
        }
    }

    public MessageView.CompanyReport blacklistCompany(User vcv,
                                                      String company) throws UserServiceException {
        // First, Confirm that the User is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new UserServiceException("Error 420: blacklistCompany(vcv, company) has failed you for VCV Staff Authentication");

        // Second, Confirm that the Company Exists
        User companyDB = userRepository.findByCompanyId(company, 1);
        if(companyDB == null) throw new UserServiceException("Error 405: blacklistCompany(vcv, company) has failed to find such an existing Company");

        // Third, Change the Company to be Blacklisted
        companyDB.setBlacklisted(true);

        try {
            // Fourth, Save the Updated Company to the Database
            userRepository.save(companyDB);
            return new MessageView.CompanyReport().build(companyDB, "Successfully Blacklisted Company");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: blacklistCompany(vcv, company) has failed to update the Company");
        }
    }


    /* Company Admin */
    public List<UserView> getEmployees(User admin) throws UserServiceException {
        // First, Confirm that the User is the Admin and they are Not Blacklisted
        if(admin != null) {
            if (!admin.isAdmin()) {
                throw new UserServiceException("Error 405: getEmployees(admin) has failed you for Admin Authentication");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (admin.isBlackisted()) {
                throw new UserServiceException("Error 410: getEmployees(admin) has failed you for Company Approved Authentication");
            }
        } else throw new UserServiceException("Error 400: getEmployees(admin) has returned null");

        List<User> employees = userRepository.findByCompanyIdOrderBySubscriptionStartDateDesc(admin.getCompanyId());
        if(employees.isEmpty()) return new UserView().build(employees);
        else throw new UserServiceException("Error 400: findByEmail(email, company) returned null");
    }

    public MessageView resetPassword(User admin,
                                     String email) throws UserServiceException {
        // First, Confirm that the User is an Admin or VCV Staff
        if(!admin.isAdmin() && admin.getCompanyType().level() != 3) {
            throw new UserServiceException("Error 405: resetPassword(admin, email) has failed to identify the User as a Company Admin or VCV Staff");
        }

        // Second, Find the Employee to Reset Password
        User employee = userRepository.findByEmail(email, admin.getCompanyId());
        if(employee == null) {
            throw new UserServiceException("Error 410: resetPassword(admin, email) has failed to find an Employee with the Email");
        }

        try {
            // Third, Set the Flag that the Password has been Reset and Saving it
            employee.setPasswordReset(true);
            userRepository.save(employee);
            return new MessageView().build(email + "'s Password has been Successfully Reset. The next time they attempt to login, " +
                    "they will be prompted to Enter a new Password");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: resetPassword(admin, email) has failed to reset the Employee's Password");
        }
    }



    public MessageView.UserReport addEmployee(User admin,
                                              User employee) throws UserServiceException {
        // First, Confirm that the User is the Admin and they are Not Blacklisted
        if(admin != null) {
            if (!admin.isAdmin()) {
                throw new UserServiceException("Error 405: addEmployee(admin, employee) has failed you for Admin Authentication");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (admin.isBlackisted()) {
                throw new UserServiceException("Error 410: addEmployee(admin, employee) has failed you for Company Approved Authentication");
            }
        } else throw new UserServiceException("Error 400: addEmployee(admin, employee) has returned null");

        // Second, Ensure that the Employee's Company Name and Type are the same as the Admin's
        employee.setCompanyId(admin.getCompanyId());
        employee.setCompanyType(admin.getCompanyType());

        try {
            // Third, Save the new Employee to the Database
            userRepository.save(employee);
            return new MessageView.UserReport().build(employee, "Successfully Added new Employee to the Company");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: addEmployee(admin, employee) has failed to add the new Employee to the Database");
        }
    }

    public MessageView.UserReport removeEmployee(User admin,
                                                 String email) throws UserServiceException {
        // First, Confirm that the User is the Admin and they are Not Blacklisted
        if(admin != null) {
            if (!admin.isAdmin()) {
                throw new UserServiceException("Error 405: removeEmployee(admin, email) has failed you for Admin Authentication");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (admin.isBlackisted()) {
                throw new UserServiceException("Error 410: removeEmployee(admin, email) has failed you for Company Approved Authentication");
            }
        } else throw new UserServiceException("Error 400: removeEmployee(admin, email) has returned null");

        // Second, Confirm that the Employee Exists
        Optional<User> employee = userRepository.findById(new User.CompositeKey(email, admin.getCompanyId()));
        if(employee.isEmpty()) throw new UserServiceException("Error 410: removeEmployee(admin, email) has failed to find an Employee with the Email");

        try {
            // Third, Remove the Employee from the Database
            userRepository.delete(employee.get());
            return new MessageView.UserReport().build(employee.get(), "Successfully Removed the Employee from the Company");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: removeEmployee(admin, email) has failed to add the new Employee to the Database");
        }
    }

    public MessageView.CompanyReport updateWebsite(User admin,
                                                   String website) throws UserServiceException {
        // First, Confirm that the User is an Admin or VCV Staff
        if(!admin.isAdmin() && admin.getCompanyType().level() != 3) {
            throw new UserServiceException("Error 405: updateWebsite(admin, website) has failed to identify the User as a Company Admin or VCV Staff");
        }

        try {
            // Second, Update the Website in the Database
            admin.setWebsite(website);
            userRepository.save(admin);
            return new MessageView.CompanyReport().build(admin,"Successfully Updated Company Website");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: updateWebsite(admin, website) has failed to update the Company website");
        }
    }

    public UserView.SubscriptionConsole renewSubscription(User admin) throws UserServiceException {
        // First, Confirm that the User is the Admin and they are Not Blacklisted
        if(admin != null) {
            if (!admin.isAdmin()) {
                throw new UserServiceException("Error 405: renewSubscription(admin) has failed you for Admin Authentication");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (admin.isBlackisted()) {
                throw new UserServiceException("Error 410: renewSubscription(admin) has failed you for Company Approved Authentication");
            }
        } else throw new UserServiceException("Error 400: renewSubscription(admin) has returned null");

        // Third, Renew the Company's Subscription Start and End Date based on the Current Date
        Timestamp start = new Timestamp(LocalDate.now().toEpochDay());
        Timestamp end = new Timestamp(LocalDate.now().withYear(1).toEpochDay());

        // Fourth, Update the Company Subscription
        admin.setSubscriptionStartDate(start);
        admin.setSubscriptionEndDate(end);
        admin.setValid(Boolean.TRUE);

        try {
            userRepository.save(admin);
            return new UserView.SubscriptionConsole().build(admin);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: renewSubscription(admin) failed to renew the Subscription");
        }
    }

    public UserView.SubscriptionConsole cancelSubscription(User admin) throws UserServiceException {
        // First, Confirm that the User is the Admin and they are Not Blacklisted
        if(admin != null) {
            if (!admin.isAdmin()) {
                throw new UserServiceException("Error 405: cancelSubscription(admin) has failed you for Admin Authentication");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (admin.isBlackisted()) {
                throw new UserServiceException("Error 410: cancelSubscription(admin) has failed you for Company Approved Authentication");
            }
        } else throw new UserServiceException("Error 400: cancelSubscription(admin) has returned null");

        // Third, Update the Company Subscription to be Cancelled
        admin.setSubscriptionEndDate(new Timestamp(LocalDate.now().toEpochDay()));
        admin.setValid(false);

        try {
            userRepository.save(admin);
            return new UserView.SubscriptionConsole().build(admin);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: cancelSubscription(admin) failed to cancel the Subscription");
        }
    }
}
