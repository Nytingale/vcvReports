package com.vcv.backend.controllers;

import com.vcv.backend.data.TestCompany;
import com.vcv.backend.data.TestUser;
import com.vcv.backend.entities.User;
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
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StaffTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;
    
    private TestUser testUser = new TestUser();
    private TestCompany testCompany = new TestCompany();

    private String baseURL = "http://localhost:";
    
    @Before
    public void setup() {
        testUser.setVcv(userRepository.findByEmailAndCompanyId("RSJMorris@gmail.com", 1L));
        testUser.setVcvString("RSJMorris@gmail.com");
        testUser.setClient(userRepository.findById("GeorgeOrwell@trident.com").get());
        testUser.setClientString("GeorgeOrwell@trident.com");
        testUser.setAdmin(userRepository.findByEmailAndCompanyId("GeorgeOrwell@trident.com", 2L));
        testUser.setAdminString("GeorgeOrwell@trident.com");
        testUser.setStaff(userRepository.findById("JaneDoe@trident.com").get());
        testUser.setStaffString("JaneDoe@trident.com");
        testUser.setNewPassword("ThisIsANewPasswordTest");
        testUser.setUsers((List<User>) userRepository.findAll());
        testCompany.setVcv(companyRepository.findById(1L).get());
        testCompany.setCompany(companyRepository.findById(2L).get());
        testCompany.setCompanyString("Trident_Insurance");
        testCompany.setCompanies(
                testUser.getUsers().stream()
                .map(u -> companyRepository.findById(u.getCompanyId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList())
        );

        baseURL += port + "/vcv/staff";
    }

    @Test
    public void canGetUsers() throws URISyntaxException, UserServiceException {
        URI uri = new URI(baseURL + "/getUsers");
        ResponseEntity<UserView[]> response = restTemplate.postForEntity(uri, testUser.getVcv(), UserView[].class);
        assertThat(Arrays.equals(response.getBody(), new UserView().build(testUser.getUsers(), testCompany.getCompanies()).toArray())).isTrue();
    }

    @Test
    public void canChangeAdmin() throws URISyntaxException {
        RequestWrapper.Employee map = new RequestWrapper.Employee();
        map.setAdmin(testUser.getAdmin());
        map.setEmployee(testUser.getStaff());
        URI uri = new URI(baseURL + "/changeAdmin");
        ResponseEntity<MessageView.UserReport> response = restTemplate.postForEntity(uri, map, MessageView.UserReport.class);
        assertThat(response.getBody().equals(new MessageView.UserReport().build(testUser.getStaff(), testCompany.getCompany(), "Successfully Changed Company Admins"))).isTrue();

        testUser.getAdmin().setRoleId(2L);
        testUser.getStaff().setRoleId(1L);
        userRepository.save(testUser.getAdmin());
        userRepository.save(testUser.getStaff());
    }

    @Test
    public void canSearchForUser() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Employee();
        map.setAdmin(testUser.getVcv());
        map.setDetails(testUser.getClientString());
        URI uri = new URI(baseURL + "/searchForUser");
        ResponseEntity<UserView> response = restTemplate.postForEntity(uri, map, UserView.class);
        assertThat(response.getBody().equals(new UserView().build(testUser.getClient(), testCompany.getCompany()))).isTrue();
    }

    @Test
    public void canSearchForCompany() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Employee();
        map.setAdmin(testUser.getVcv());
        map.setDetails(testCompany.getCompanyString());
        URI uri = new URI(baseURL + "/searchForCompany");
        ResponseEntity<CompanyView> response = restTemplate.postForEntity(uri, map, CompanyView.class);
        assertThat(response.getBody().equals(new CompanyView().build(testCompany.getCompany()))).isTrue();
    }

    @Test
    public void canAddEmployee() throws URISyntaxException {
        RequestWrapper.Employee map = new RequestWrapper.Employee();
        map.setAdmin(testUser.getVcv());
        map.setEmployee(testUser.getNewEmployee());
        URI uri = new URI(baseURL + "/addEmployee");
        ResponseEntity<MessageView.UserReport> response = restTemplate.postForEntity(uri, map, MessageView.UserReport.class);
        assertThat(response.getBody().equals(new MessageView.UserReport().build(testUser.getNewEmployee(), testCompany.getCompany(), "Successfully Added new Employee to the Company"))).isTrue();

        userRepository.delete(testUser.getNewEmployee());
    }

    @Test
    public void canRemovedEmployee() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Employee();
        map.setAdmin(testUser.getVcv());
        map.setDetails(testUser.getStaffString());
        URI uri = new URI(baseURL + "/removeEmployee");
        ResponseEntity<MessageView.UserReport> response = restTemplate.postForEntity(uri, map, MessageView.UserReport.class);
        assertThat(response.getBody().equals(new MessageView.UserReport().build(testUser.getStaff(), testCompany.getCompany(), "Successfully Removed the Employee from the Company"))).isTrue();

        userRepository.save(testUser.getStaff());
    }

    @Test
    public void canRegisterCompany() throws URISyntaxException {
        RequestWrapper.Registration map = new RequestWrapper.Registration();
        map.setVcv(testUser.getVcv());
        map.setAdmin(testUser.getNewAdmin());
        map.setCompany(testCompany.getNewCompany());
        URI uri = new URI(baseURL + "/registerCompany");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, map, MessageView.CompanyReport.class);
        assertThat(response.getBody().equals(new MessageView.CompanyReport().build(testCompany.getNewCompany(), "Successfully Created Company"))).isTrue();

        companyRepository.delete(testCompany.getNewCompany());
        userRepository.delete(testUser.getNewAdmin());
    }

    @Test
    public void canBlacklistCompany() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Admin();
        map.setAdmin(testUser.getVcv());
        map.setDetails(testCompany.getCompanyString());
        URI uri = new URI(baseURL + "/blacklistCompany");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, map, MessageView.CompanyReport.class);
        assertThat(response.getBody().equals(new MessageView.CompanyReport().build(testCompany.getBlacklistedCompany(), "Successfully Blacklisted Company"))).isTrue();

        testCompany.getCompany().setValid(true);
        companyRepository.save(testCompany.getCompany());
    }

    @Test
    public void canApproveCompany() throws URISyntaxException {
        testCompany.getCompany().setBlacklisted(true);
        companyRepository.save(testCompany.getCompany());

        RequestWrapper.Admin map = new RequestWrapper.Admin();
        map.setAdmin(testUser.getVcv());
        map.setDetails(testCompany.getCompanyString());
        URI uri = new URI(baseURL + "/approveCompany");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, map, MessageView.CompanyReport.class);
        assertThat(response.getBody().equals(new MessageView.CompanyReport().build(testCompany.getCompany(), "Successfully Approved Company"))).isTrue();
    }

    @Test
    public void canChangePassword() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Admin();
        map.setAdmin(testUser.getVcv());
        map.setDetails(testUser.getNewPassword());
        URI uri = new URI(baseURL + "/changePassword");
        ResponseEntity<MessageView.UserReport> response = restTemplate.postForEntity(uri, map, MessageView.UserReport.class);
        assertThat(response.getBody().equals(new MessageView.UserReport().build(testUser.getVcv(), testCompany.getVcv(), "Successfully Changed Password"))).isTrue();

        testUser.getVcv().setPassword(testUser.getOldPassword());
        userRepository.save(testUser.getVcv());
    }
}
