package com.vcv.backend.services;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.UserServiceException;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.RoleRepository;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.utilities.Utils;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private CompanyRepository companyRepository;

    /* VCV Staff */
    public UserView searchForUser(User vcv,
                                  String email) throws UserServiceException {
        // First, Confirm that the Admin is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new UserServiceException("Error 420: searchForUser(vcv, email) has failed you for VCV Staff Authentication");

        Optional<User> user = userRepository.findById(email);
        if(user.isPresent()) {
            Optional<Company> company = companyRepository.findById(user.get().getCompanyId());
            if(company.isPresent()) return new UserView().build(user.get(), company.get());
            else throw new UserServiceException("Error 400: searchForUser(vcv, email) returned null");
        } else throw new UserServiceException("Error 400: searchForUser(vcv, email) returned null");
    }

    public List<UserView> getUsers(User vcv) throws UserServiceException {
        // First, Confirm that the Admin is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new UserServiceException("Error 420: getUsers(vcv) has failed you for VCV Staff Authentication");

        // Second, Find the Clients to Return
        List<User> users = (List<User>) userRepository.findAll();
        if(!users.isEmpty()) {
            List<Company> companies = new ArrayList<>();
            for(User user: users) {
                companyRepository.findById(user.getCompanyId()).ifPresent(companies::add);
            }

            return new UserView().build(users, companies);
        } else throw new UserServiceException("Error 400: getUsers(vcv) returned null");
    }



    /* Company Admin / VCV Staff */
    public MessageView resetPassword(User admin,
                                     String email) throws UserServiceException {
        // First, Confirm that the Admin is an Admin or VCV Staff
        if (!Utils.isValidStaffOrAdmin(admin)) {
            throw new UserServiceException("Error 420: resetPassword(admin, email) has failed to identify the Admin as a Company Admin or VCV Staff");
        }

        // Second, Find the Employee to Reset Password
        Optional<User> employee = userRepository.findById(email);
        if(employee.isEmpty()) {
            throw new UserServiceException("Error 405: resetPassword(admin, email) has failed to find an Employee with the Email");
        }

        try {
            // Third, Set the Flag that the Password has been Reset and Saving it
            employee.get().setPasswordReset(true);
            userRepository.save(employee.get());
            return new MessageView().build(email + "'s Password has been Successfully Reset. The next time they attempt to login, " +
                    "they will be prompted to Enter a new Password");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: resetPassword(admin, email) has failed to reset the Employee's Password");
        }
    }

    public MessageView.UserReport changeAdmin(User admin,
                                              User employee) throws UserServiceException {
        // First, Confirm that the Admin is an Admin or VCV Staff
        if(!Utils.isValidStaffOrAdmin(admin)) throw new UserServiceException("Error 420: changeAdmin(admin, employee) has failed to identify the Admin as a Company Admin or VCV Staff");

        // Second, Find the Company of the Employee
        Company company = companyRepository.findById(employee.getCompanyId()).get();
        if(admin != null) {

            // Third, Ensure the Admin and the Employee share the Same Company
            if(!admin.getCompanyId().equals(employee.getCompanyId())) {
                throw new UserServiceException("Error 425: changeAdmin(admin, employee) has identified the Admin and the Employee from two separate Companies");
            }

            // Fourth, Swap the Roles and Website Information of the Employee and Admin
            company.setAdmin(employee.getEmail());
            employee.setRoleId(2L);
            admin.setRoleId(1L);

            try {
                // Fifth, Update the new records of the Employee and the Admin
                userRepository.save(admin);
                userRepository.save(employee);
                return new MessageView.UserReport().build(employee, company, "Successfully Changed Company Admins");
            } catch (Exception e) {
                e.printStackTrace();
                throw new UserServiceException("Error 415: changeAdmin(admin, employee) failed to change the Company Admin");
            }
        } else throw new UserServiceException("Error 400: changeAdmin(admin, employee) returned null");
    }

    public MessageView.UserReport addEmployee(User admin,
                                              User employee) throws UserServiceException {
        // First, Confirm that the Admin is an Admin or VCV Staff
        if(!Utils.isValidStaffOrAdmin(admin)) throw new UserServiceException("Error 420: addEmployee(admin, employee) has failed to identify the Admin as a Company Admin or VCV Staff");

        // Third, Ensure the Email doesn't Already Exist
        Optional<User> employeeDB = userRepository.findById(employee.getEmail());
        if(employeeDB.isPresent()) {
            throw new UserServiceException("Error 425: addEmployee(admin, employee) cannot add this Employee as the Email already exists");
        }

        // Fourth, Ensure the Employee's Password is Valid
        if(Utils.isValidPassword(employee.getPassword()) == null) {
            throw new UserServiceException("Error 430: addEmployee(admin, employee) cannot add this Employee as the Password is not valid");
        }

        // Fifth, Ensure that the Employee's Company Name and Type are the same as the Admin's
        employee.setCompanyId(admin.getCompanyId());

        // Sixth, Encode the Employee's Password
        employee.setPassword(new BCryptPasswordEncoder(11).encode(employee.getPassword()));

        // Seventh, Check if the Admin is a VCV Staff and if so, Change the Employee's Role ID
        if(admin.getRoleId() == 3) {
            employee.setRoleId(3L);
        }

        try {
            Company company = companyRepository.findById(admin.getCompanyId()).get();

            // Seventh, Save the new Employee to the Database
            userRepository.save(employee);
            return new MessageView.UserReport().build(employee, company, "Successfully Added new Employee to the Company");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: addEmployee(admin, employee) has failed to add the new Employee to the Database");
        }
    }

    public MessageView.UserReport removeEmployee(User admin,
                                                 String email) throws UserServiceException {
        // First, Confirm that the Admin is an Admin or VCV Staff
        if(!Utils.isValidStaffOrAdmin(admin)) throw new UserServiceException("Error 420: removeEmployee(admin, employee) has failed to identify the Admin as a Company Admin or VCV Staff");

        // Second, Confirm that the Employee Exists
        Optional<User> employee = userRepository.findById(email);
        if(employee.isEmpty()) throw new UserServiceException("Error 410: removeEmployee(admin, email) has failed to find an Employee with the Email");

        try {
            Company company = companyRepository.findById(admin.getCompanyId()).get();

            // Third, Remove the Employee from the Database
            userRepository.delete(employee.get());
            return new MessageView.UserReport().build(employee.get(), company, "Successfully Removed the Employee from the Company");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: removeEmployee(admin, email) has failed to add the new Employee to the Database");
        }
    }


    /* Company Admin */
    public List<UserView> getEmployees(User admin) throws UserServiceException {
        // First, Confirm that the Admin is the Admin and they are Not Blacklisted
        Optional<Company> company = companyRepository.findById(admin.getCompanyId());
        if(company.isPresent()) {
            if (admin.getRoleId() != 2L) {
                throw new UserServiceException("Error 405: getEmployees(admin) has failed you for Admin Authentication");
            }

            // Second, Confirm that the Company is not Blacklisted
            if (company.get().getBlacklisted()) {
                throw new UserServiceException("Error 410: getEmployees(admin) has failed you for Company Approved Authentication");
            }
        } else throw new UserServiceException("Error 400: getEmployees(admin) has returned null");

        List<User> employees = userRepository.findByCompanyId(admin.getCompanyId());
        if(!employees.isEmpty()) return new UserView().build(employees, company.get());
        else throw new UserServiceException("Error 400: findByEmail(email, company) returned null");
    }



    /* Miscellaneous */
    public MessageView.UserReport changePassword(User user,
                                                 String newPassword) throws UserServiceException {
        // First, Encode the New Password
        user.setPassword(new BCryptPasswordEncoder(11).encode(newPassword));

        try {
            Company company = companyRepository.findById(user.getCompanyId()).get();

            // Second, Update the Admin Record to the Database
            userRepository.save(user);
            return new MessageView.UserReport().build(user, company, "Successfully Changed Password");
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserServiceException("Error 415: changePassword(user, newPassword) has failed to change the Admin's Password");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(email);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                user.get().getPassword(),
                (Collection<? extends GrantedAuthority>) mapRoleToAuthority(user.get().getRoleId()));
    }

    private GrantedAuthority mapRoleToAuthority(Long roleID){
        return new SimpleGrantedAuthority(roleRepository.findById(roleID).get().getName());
    }
}
