package com.vcv.backend.controllers;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.User;
import com.vcv.backend.enums.CompanyType;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.UserRepository;

import com.vcv.backend.views.CompanyView;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StaffTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;

    Map<String, User> stringUserMap = Collections.emptyMap();
    Map<String, Object> stringObjectMap = Collections.emptyMap();

    String clientString = "T_man@hotmail.com";
    String companyString = "CourtesyGarage";
    String newPasswordString = "ThisIsANewPasswordTest";

    User newEmployee = new User.Builder()
            .setEmail("TestEmail@email.com")
            .setPassword("newEmailTest123")
            .setPasswordReset(false)
            .setCompanyId(1L)
            .setRoleId(3L)
            .build();

    User newAdmin = new User.Builder()
            .setEmail("TestEmail1@email.com")
            .setPassword("newEmailTest123")
            .setPasswordReset(false)
            .setCompanyId(1L)
            .setRoleId(2L)
            .build();

    Company newCompany = new Company.Builder()
            .setId(7L)
            .setCompanyName("TestNameCompany")
            .setCompanyType(CompanyType.DEALERSHIP)
            .setSubscriptionStartDate(Timestamp.valueOf(LocalDateTime.now()))
            .setSubscriptionEndDate(Timestamp.valueOf(LocalDateTime.now().plusYears(1)))
            .setWebsite("http://stuff.com")
            .setAdmin("TestEmail1@email.com")
            .setBlacklisted(false)
            .setValid(true)
            .build();

    User vcvStaff = userRepository.findByEmailAndCompanyId("RSJMorris@gmail.com", 1L);
    User testClient = userRepository.findById(clientString).get();
    User nonAdminEmployee = userRepository.findByEmailAndCompanyId("JaneDoe@trident.com", 2L);

    List<User> testUsers = (List<User>) userRepository.findAll();

    Company testCompany = companyRepository.findById(3L).get();

    public static class UserViewList {
        List<UserView> userViews;

        public UserViewList() {
            userViews = new ArrayList<>();
        }
        public List<UserView> getUserViews() {
            return userViews;
        }
    }

    @Test
    public void canGetUsers() {
        UserViewList response = restTemplate.postForObject("http://localhost:" + port + "/getUsers", vcvStaff, UserViewList.class);
        assertThat(response.getUserViews()).isEqualTo(new UserView().build(testUsers));
    }

    @Test
    public void canChangeAdmin() {
        stringUserMap.put("VCV", vcvStaff);
        stringUserMap.put("Employee", nonAdminEmployee);

        MessageView.UserReport response = restTemplate.postForObject("http://localhost:" + port + "/changeAdmin", stringUserMap, MessageView.UserReport.class);
        assertThat(response).isEqualTo(new MessageView.UserReport().build(nonAdminEmployee,"Successfully Changed Company Admins"));
    }

    @Test
    public void canSearchForUser() {
        stringObjectMap.put("VCV", vcvStaff);
        stringObjectMap.put("Client", clientString);

        UserView response = restTemplate.postForObject("http://localhost:" + port + "/searchForUser", stringObjectMap, UserView.class);
        assertThat(response).isEqualTo(new UserView().build(testClient));
    }

    @Test
    public void canSearchForCompany() {
        stringObjectMap.put("VCV", vcvStaff);
        stringObjectMap.put("Company", companyString);

        CompanyView response = restTemplate.postForObject("http://localhost:" + port + "/searchForCompany", stringObjectMap, CompanyView.class);
        assertThat(response).isEqualTo(new CompanyView().build(testCompany));
    }

    @Test
    public void canAddEmployee() {
        stringUserMap.put("VCV", vcvStaff);
        stringUserMap.put("Employee", newEmployee);

        MessageView.UserReport response = restTemplate.postForObject("http://localhost:" + port + "/addEmployee", stringUserMap, MessageView.UserReport.class);
        assertThat(response).isEqualTo(new MessageView.UserReport().build(newEmployee, "Successfully Added new Employee to the Company"));
    }

    @Test
    public void canRegisterCompany() {
        stringObjectMap.put("VCV", vcvStaff);
        stringObjectMap.put("Admin", newAdmin);
        stringObjectMap.put("Company", newCompany);

        MessageView.CompanyReport response = restTemplate.postForObject("http://localhost:" + port + "/registerCompany", stringObjectMap, MessageView.CompanyReport.class);
        assertThat(response).isEqualTo(new MessageView.CompanyReport().build(newCompany, "Successfully Created Company"));
    }

    @Test
    public void canBlacklistCompany() {
        stringObjectMap.put("VCV", vcvStaff);
        stringObjectMap.put("Company", companyString);

        MessageView.CompanyReport response = restTemplate.postForObject("http://localhost:" + port + "/blacklistCompany", stringObjectMap, MessageView.CompanyReport.class);
        assertThat(response).isEqualTo(new MessageView.CompanyReport().build(testCompany, "Successfully Blacklisted Company"));
    }

    @Test
    public void canApproveCompany() {
        stringObjectMap.put("VCV", vcvStaff);
        stringObjectMap.put("Company", companyString);

        MessageView.CompanyReport response = restTemplate.postForObject("http://localhost:" + port + "/approveCompany", stringObjectMap, MessageView.CompanyReport.class);
        assertThat(response).isEqualTo(new MessageView.CompanyReport().build(testCompany, "Successfully Approved Company"));
    }

    @Test
    public void canChangePassword() {
        stringObjectMap.put("User", vcvStaff);
        stringObjectMap.put("New Password", newPasswordString);

        MessageView.UserReport response = restTemplate.postForObject("http://localhost:" + port + "/changePassword", stringObjectMap, MessageView.UserReport.class);
        assertThat(response).isEqualTo(new MessageView.UserReport().build(vcvStaff, "Successfully Approved Company"));
    }
}
