package com.vcv.backend.controllers;

import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.data.TestCompany;
import com.vcv.backend.data.TestUser;
import com.vcv.backend.entities.Company;
import com.vcv.backend.exceptions.UserServiceException;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.utilities.RequestWrapper;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;

    private TestUser testUser = new TestUser();
    private TestCompany testCompany = new TestCompany();
    
    private String baseURL = "http://localhost:";

    @Before
    public void setup() {
        testUser.setAdminString("GeorgeOrwell@trident.com");
        testUser.setStaffString("JaneDoe@trident.com");
        testUser.setAdmin(userRepository.findById("GeorgeOrwell@trident.com").get());
        testUser.setStaff(userRepository.findById("JaneDoe@trident.com").get());
        testUser.setUsers(userRepository.findByCompanyId(2L));
        testCompany.setCompany(companyRepository.findById(2L).get());
        testCompany.setCompanies((List<Company>) companyRepository.findAllById(testUser.getUsers().stream().map(user -> user.getCompanyId()).collect(Collectors.toList())));

        baseURL += port + "/vcv/admin";
    }

    @Test
    public void canGetEmployees() throws URISyntaxException, UserServiceException {
        URI uri = new URI(baseURL + "/getEmployees");
        System.out.println("Users: " + testUser.getUsers().size() + ", Companies: " + testCompany.getCompanies().size());
        ResponseEntity<UserView[]> response = restTemplate.postForEntity(uri, testUser.getAdmin(), UserView[].class);
        assertThat(Arrays.equals(response.getBody(), new UserView().build(testUser.getUsers(), testCompany.getCompany()).toArray())).isTrue();
    }

    @Test
    public void canCancelSubscription() throws URISyntaxException {
        URI uri = new URI(baseURL + "/cancelSubscription");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, testUser.getAdmin(), MessageView.CompanyReport.class);
        assertThat(response.getBody().equals(new MessageView.CompanyReport().build(testCompany.getCancelledCompany(), "Successfully Cancelled Subscription"))).isTrue();
    }

    @Test
    public void canRenewSubscription() throws URISyntaxException {
        URI uri = new URI(baseURL + "/renewSubscription");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, testUser.getAdmin(), MessageView.CompanyReport.class);
        assertThat(response.getBody().equals(new MessageView.CompanyReport().build(testCompany.getRenewedCompany(), "Successfully Renewed Subscription"))).isTrue();
    }

    @Test
    public void canResetPassword() throws URISyntaxException {
        RequestWrapper.Admin map = new RequestWrapper.Admin();
        map.setAdmin(testUser.getAdmin());
        map.setDetails(testUser.getStaffString());
        URI uri = new URI(baseURL + "/resetPassword");
        ResponseEntity<MessageView> response = restTemplate.postForEntity(uri, map, MessageView.class);
        assertThat(response.getBody().equals(new MessageView().build(testUser.getStaffString() + "'s Password has been Successfully Reset. The next time they attempt " +
                "to login, they will be prompted to Enter a new Password"))).isTrue();

        testUser.getStaff().setPasswordReset(false);
        userRepository.save(testUser.getStaff());
    }

    @Test
    public void canUploadImage() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("test.jpg").getFile());
            MultipartFile image = new MockMultipartFile("Owl.jpg", new FileInputStream(file));

            RequestWrapper.Image map = new RequestWrapper.Image();
            map.setAdmin(testUser.getAdmin());
            map.setImage(image);
            URI uri = new URI(baseURL + "/uploadImage");
            ResponseEntity<MessageView.FileUpload> response = restTemplate.postForEntity(uri, map, MessageView.FileUpload.class);
            assertThat(response.getBody().equals(new MessageView.FileUpload().build(image, testCompany.getCompany().getCompanyName(), "Successfully Uploaded Image"))).isTrue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
