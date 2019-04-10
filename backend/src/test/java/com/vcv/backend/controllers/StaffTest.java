package com.vcv.backend.controllers;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.User;
import com.vcv.backend.enums.CompanyType;
import com.vcv.backend.exceptions.UserServiceException;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.UserRepository;

import com.vcv.backend.utilities.RequestWrapper;
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
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StaffTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;
    
    String clientString = "T_man@hotmail.com";
    String companyString = "CourtesyGarage";
    String newPasswordString = "ThisIsANewPasswordTest";
    String adminEmailString = "GeorgeOrwell@trident.com";
    String nonAdminEmailString = "JaneDoe@trident.com";
    String vcvStaffEmailString = "RSJMorris@gmail.com";

    String baseURL;

    User vcvStaff;
    User testClient;
    User adminEmployee;
    User nonAdminEmployee;
    Company vcvCompany;
    Company testCompany;

    List<User> testUsers;
    List<Company> testCompanies;

    User newEmployee = new User.Builder()
            .setEmail("TestEmail@email.com")
            .setPassword("newEmailTest123")
            .setPasswordReset(false)
            .setCompanyId(3L)
            .setRoleId(2L)
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

    @Before
    public void setup() {
        vcvStaff = userRepository.findByEmailAndCompanyId(vcvStaffEmailString, 1L);
        testClient = userRepository.findById(clientString).get();
        adminEmployee = userRepository.findByEmailAndCompanyId(adminEmailString, 2L);
        nonAdminEmployee = userRepository.findByEmailAndCompanyId(nonAdminEmailString, 2L);
        vcvCompany = companyRepository.findById(1L).get();
        testCompany = companyRepository.findById(3L).get();

        testUsers = (List<User>) userRepository.findAll();

        testCompanies = new ArrayList<>();
        for(User testUser: testUsers) {
            companyRepository.findById(testUser.getCompanyId()).ifPresent(testCompanies::add);
        }

        baseURL = "http://localhost:" + port + "/vcv/staff";
    }

    @Test
    public void canGetUsers() throws URISyntaxException, UserServiceException {
        URI uri = new URI(baseURL + "/getUsers");
        ResponseEntity<UserView[]> response = restTemplate.postForEntity(uri, vcvStaff, UserView[].class);
        assertThat(Arrays.equals(response.getBody(), new UserView().build(testUsers, testCompanies).toArray())).isTrue();
    }

    @Test
    public void canChangeAdmin() throws URISyntaxException {
        RequestWrapper.Employee map = new RequestWrapper.Employee();
        map.setAdmin(vcvStaff);
        map.setEmployee(nonAdminEmployee);
        URI uri = new URI(baseURL + "/changeAdmin");
        ResponseEntity<MessageView.UserReport> response = restTemplate.postForEntity(uri, map, MessageView.UserReport.class);
        assertThat(response.getBody().equals(new MessageView.UserReport().build(nonAdminEmployee, testCompany, "Successfully Changed Company Admins"))).isTrue();
        adminEmployee.setRoleId(2L);
        nonAdminEmployee.setRoleId(1L);
        userRepository.save(adminEmployee);
        userRepository.save(nonAdminEmployee);
    }

    @Test
    public void canSearchForUser() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Employee();
        map.setAdmin(vcvStaff);
        map.setDetails(clientString);
        URI uri = new URI(baseURL + "/searchForUser");
        ResponseEntity<UserView> response = restTemplate.postForEntity(uri, map, UserView.class);
        assertThat(response.getBody().equals(new UserView().build(testClient, testCompany))).isTrue();
    }

    @Test
    public void canSearchForCompany() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Employee();
        map.setAdmin(vcvStaff);
        map.setDetails(companyString);
        URI uri = new URI(baseURL + "/searchForCompany");
        ResponseEntity<CompanyView> response = restTemplate.postForEntity(uri, map, CompanyView.class);
        assertThat(response.getBody().equals(new CompanyView().build(testCompany))).isTrue();
    }

    @Test
    public void canAddEmployee() throws URISyntaxException {
        RequestWrapper.Employee map = new RequestWrapper.Employee();
        map.setAdmin(vcvStaff);
        map.setEmployee(newEmployee);
        URI uri = new URI(baseURL + "/addEmployee");
        ResponseEntity<MessageView.UserReport> response = restTemplate.postForEntity(uri, map, MessageView.UserReport.class);
        assertThat(response.getBody().equals(new MessageView.UserReport().build(newEmployee, testCompany, "Successfully Added new Employee to the Company"))).isTrue();
        userRepository.delete(newEmployee);
    }

    @Test
    public void canRegisterCompany() throws URISyntaxException {
        RequestWrapper.Registration map = new RequestWrapper.Registration();
        map.setVcv(vcvStaff);
        map.setAdmin(newAdmin);
        map.setCompany(newCompany);
        URI uri = new URI(baseURL + "/registerCompany");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, map, MessageView.CompanyReport.class);
        assertThat(response.getBody().equals(new MessageView.CompanyReport().build(newCompany, "Successfully Created Company"))).isTrue();
    }

    @Test
    public void canBlacklistCompany() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Admin();
        map.setAdmin(vcvStaff);
        map.setDetails(companyString);
        URI uri = new URI(baseURL + "/blacklistCompany");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, map, MessageView.CompanyReport.class);
        assertThat(response.getBody().equals(new MessageView.CompanyReport().build(testCompany, "Successfully Blacklisted Company"))).isTrue();
    }

    @Test
    public void canApproveCompany() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Admin();
        map.setAdmin(vcvStaff);
        map.setDetails(companyString);
        URI uri = new URI(baseURL + "/approveCompany");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, map, MessageView.CompanyReport.class);
        assertThat(response.getBody().equals(new MessageView.CompanyReport().build(testCompany, "Successfully Approved Company"))).isTrue();
    }

    @Test
    public void canChangePassword() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Admin();
        map.setAdmin(vcvStaff);
        map.setDetails(newPasswordString);
        URI uri = new URI(baseURL + "/changePassword");
        ResponseEntity<MessageView.UserReport> response = restTemplate.postForEntity(uri, map, MessageView.UserReport.class);
        assertThat(response.getBody().equals(new MessageView.UserReport().build(vcvStaff, vcvCompany, "Successfully Changed Password"))).isTrue();
    }
}
