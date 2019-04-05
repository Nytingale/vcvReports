package com.vcv.backend.controllers;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.User;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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

    User admin = userRepository.findById("GeorgeOrwell@trident.com").get();
    Company company = companyRepository.findById(2L).get();

    List<User> testUsers = userRepository.findByCompanyId(2L);

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
    public void canGetEmployees() {
        UserViewList response = restTemplate.postForObject("http://localhost:" + port + "/getEmployees", admin, UserViewList.class);
        assertThat(response.getUserViews()).isEqualTo(new UserView().build(testUsers));
    }

    @Test
    public void canCancelSubscription() {
        MessageView.CompanyReport response = restTemplate.postForObject("http://localhost:" + port + "/cancelSubscription", admin, MessageView.CompanyReport.class);
        assertThat(response).isEqualTo(new MessageView.CompanyReport().build(company, "Successfully Cancelled Subscription"));
    }

    @Test
    public void canRenewSubscription() {
        MessageView.CompanyReport response = restTemplate.postForObject("http://localhost:" + port + "/renewSubscription", admin, MessageView.CompanyReport.class);
        assertThat(response).isEqualTo(new MessageView.CompanyReport().build(company, "Successfully Renewed Subscription"));
    }

    @Test
    public void canResetPassword() {
        stringObjectMap.put("Admin", admin);
        stringObjectMap.put("Email", emailString);

        MessageView response = restTemplate.postForObject("http://localhost:" + port + "/resetPassword", stringObjectMap, MessageView.class);
        assertThat(response).isEqualTo(new MessageView().build(emailString + "'s Password has been Successfully Reset. The next time they attempt " +
                "to login, they will be prompted to Enter a new Password"));
    }

    @Test
    public void canUploadImage() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("test.jpg").getFile());
            MultipartFile image = new MockMultipartFile("Owl.jpg", new FileInputStream(file));

            stringObjectMap.put("Admin", admin);
            stringObjectMap.put("Image", image);

            MessageView.FileUpload response = restTemplate.postForObject("http://localhost:" + port + "/uploadImage", stringObjectMap, MessageView.FileUpload.class);
            assertThat(response).isEqualTo(new MessageView.FileUpload().build(image, company.getCompanyName(), "Successfully Uploaded Image"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
