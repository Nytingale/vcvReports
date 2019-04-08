package com.vcv.backend.controllers;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.User;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.UserRepository;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;

    Map<String, Object> stringObjectMap = Collections.emptyMap();

    String emailString = "JaneDoe@trident.com";
    String adminEmailString = "GeorgeOrwell@trident.com";

    String baseURL;

    User admin;
    Company company;
    List<User> testUsers;

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
        admin = userRepository.findById(adminEmailString).get();
        company = companyRepository.findById(2L).get();
        testUsers = userRepository.findByCompanyId(2L);

        baseURL = "http://localhost:" + port + "/vcv/admin";
    }

    @Test
    public void canGetEmployees() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getEmployees");
        ResponseEntity<UserViewList> response = restTemplate.postForEntity(uri, admin, UserViewList.class);
        assertThat(response.getBody().getUserViews()).isEqualTo(new UserView().build(testUsers));
    }

    @Test
    public void canCancelSubscription() throws URISyntaxException {
        URI uri = new URI(baseURL + "/cancelSubscription");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, admin, MessageView.CompanyReport.class);
        assertThat(response.getBody()).isEqualTo(new MessageView.CompanyReport().build(company, "Successfully Cancelled Subscription"));
    }

    @Test
    public void canRenewSubscription() throws URISyntaxException {
        URI uri = new URI(baseURL + "/renewSubscription");
        ResponseEntity<MessageView.CompanyReport> response = restTemplate.postForEntity(uri, admin, MessageView.CompanyReport.class);
        assertThat(response.getBody()).isEqualTo(new MessageView.CompanyReport().build(company, "Successfully Renewed Subscription"));
    }

    @Test
    public void canResetPassword() throws URISyntaxException {
        stringObjectMap.put("Admin", admin);
        stringObjectMap.put("Email", emailString);
        URI uri = new URI(baseURL + "/resetPassword");
        ResponseEntity<MessageView> response = restTemplate.postForEntity(uri, stringObjectMap, MessageView.class);
        assertThat(response.getBody()).isEqualTo(new MessageView().build(emailString + "'s Password has been Successfully Reset. The next time they attempt " +
                "to login, they will be prompted to Enter a new Password"));
    }

    @Test
    public void canUploadImage() throws URISyntaxException {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("test.jpg").getFile());
            MultipartFile image = new MockMultipartFile("Owl.jpg", new FileInputStream(file));

            stringObjectMap.put("Admin", admin);
            stringObjectMap.put("Image", image);
            URI uri = new URI(baseURL + "/uploadImage");
            ResponseEntity<MessageView.FileUpload> response = restTemplate.postForEntity(uri, stringObjectMap, MessageView.FileUpload.class);
            assertThat(response.getBody()).isEqualTo(new MessageView.FileUpload().build(image, company.getCompanyName(), "Successfully Uploaded Image"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
