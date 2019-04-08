package com.vcv.backend.controllers;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.User;
import com.vcv.backend.enums.CompanyType;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.UserRepository;

import com.vcv.backend.views.CompanyView;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
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
    String nonAdminEmailString = "JaneDoe@trident.com";
    String vcvStaffEmailString = "RSJMorris@gmail.com";

    String baseURL;

    User vcvStaff;
    User testClient;
    User nonAdminEmployee;
    Company testCompany;
    List<User> testUsers;

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
            .setCompanyType(CompanyType.Dealership)
            .setSubscriptionStartDate(Timestamp.valueOf(LocalDateTime.now()))
            .setSubscriptionEndDate(Timestamp.valueOf(LocalDateTime.now().plusYears(1)))
            .setWebsite("http://stuff.com")
            .setAdmin("TestEmail1@email.com")
            .setBlacklisted(false)
            .setValid(true)
            .build();

    public class UserViewList {
        List<UserView> userViews;

        public UserViewList() {
            userViews = new ArrayList<>();
        }
        public List<UserView> getUserViews() {
            return userViews;
        }
    }

    @Before
    public void setup() {
        vcvStaff = userRepository.findByEmailAndCompanyId(vcvStaffEmailString, 1L);
        testClient = userRepository.findById(clientString).get();
        nonAdminEmployee = userRepository.findByEmailAndCompanyId(nonAdminEmailString, 2L);
        testCompany = companyRepository.findById(3L).get();
        testUsers = (List<User>) userRepository.findAll();

        baseURL = "http://localhost:" + port + "/vcv/staff";
    }

    @Test
    public void canGetUsers() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getUsers");
        ResponseEntity<UserViewList> response = restTemplate.postForEntity(uri, vcvStaff, UserViewList.class);
        assertThat(response.getBody().getUserViews()).isEqualTo(new UserView().build(testUsers));
    }

    @Test
    public void canChangeAdmin() throws URISyntaxException {
        stringUserMap.put("VCV", vcvStaff);
        stringUserMap.put("Employee", nonAdminEmployee);
        URI uri = new URI(baseURL + "/changeAdmin");
        ResponseEntity<MessageView.UserReport> response = restTemplate.postForEntity(uri, stringUserMap, MessageView.UserReport.class);
        assertThat(response.getBody()).isEqualTo(new MessageView.UserReport().build(nonAdminEmployee,"Successfully Changed Company Admins"));
    }

    @Test
    public void canSearchForUser() throws URISyntaxException {
        stringObjectMap.put("VCV", vcvStaff);
        stringObjectMap.put("Client", clientString);
        URI uri = new URI(baseURL + "/searchForUser");
        ResponseEntity<UserView> response = restTemplate.postForEntity(uri, stringObjectMap, UserView.class);
        assertThat(response.getBody()).isEqualTo(new UserView().build(testClient));
    }

    @Test
    public void canSearchForCompany() throws URISyntaxException {
        stringObjectMap.put("VCV", vcvStaff);
        stringObjectMap.put("Company", companyString);
        URI uri = new URI(baseURL + "/searchForCompany");
        ResponseEntity<CompanyView> response = restTemplate.postForEntity(uri, stringObjectMap, CompanyView.class);
        assertThat(response.getBody()).isEqualTo(new CompanyView().build(testCompany));
    }

    @Test
    public void canAddEmployee() throws URISyntaxException {
        stringUserMap.put("VCV", vcvStaff);
        stringUserMap.put("Employee", newEmployee);
        URI uri = new URI(baseURL + "/addEmployee");
        ResponseEntity<MessageView.UserReport> response = restTemplate.postForEntity(uri, stringUserMap, MessageView.UserReport.class);
        assertThat(response.getBody()).isEqualTo(new MessageView.UserReport().build(newEmployee, "Successfully Added new Employee to the Company"));
    }

    @Test
    public void canRegisterCompany() throws URISyntaxException {
        stringObjectMap.put("VCV", vcvStaff);
        stringObjectMap.put("Admin", newAdmin);
        stringObjectMap.put("Company", newCompany);
        URI uri = new URI(baseURL + "/registerCompany");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, stringObjectMap, MessageView.CompanyReport.class);
        assertThat(response.getBody()).isEqualTo(new MessageView.CompanyReport().build(newCompany, "Successfully Created Company"));
    }

    @Test
    public void canBlacklistCompany() throws URISyntaxException {
        stringObjectMap.put("VCV", vcvStaff);
        stringObjectMap.put("Company", companyString);
        URI uri = new URI(baseURL + "/blacklistCompany");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, stringObjectMap, MessageView.CompanyReport.class);
        assertThat(response.getBody()).isEqualTo(new MessageView.CompanyReport().build(testCompany, "Successfully Blacklisted Company"));
    }

    @Test
    public void canApproveCompany() throws URISyntaxException {
        stringObjectMap.put("VCV", vcvStaff);
        stringObjectMap.put("Company", companyString);
        URI uri = new URI(baseURL + "/approveCompany");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, stringObjectMap, MessageView.CompanyReport.class);
        assertThat(response.getBody()).isEqualTo(new MessageView.CompanyReport().build(testCompany, "Successfully Approved Company"));
    }

    @Test
    public void canChangePassword() throws URISyntaxException {
        stringObjectMap.put("User", vcvStaff);
        stringObjectMap.put("New Password", newPasswordString);
        URI uri = new URI(baseURL + "/changePassword");
        ResponseEntity<MessageView.UserReport> response = restTemplate.postForEntity(uri, stringObjectMap, MessageView.UserReport.class);
        assertThat(response.getBody()).isEqualTo(new MessageView.UserReport().build(vcvStaff, "Successfully Changed Password"));
    }
}
