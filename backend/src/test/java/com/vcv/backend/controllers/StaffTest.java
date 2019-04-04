package com.vcv.backend.controllers;

import com.vcv.backend.entities.User;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.UserRepository;

import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.test.context.junit4.SpringRunner;

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

    List<User> testUsers = (List<User>) userRepository.findAll();
    User vcvStaff = userRepository.findByEmailAndCompanyId("RSJMorris@gmail.com", 1L);
    User newAdmin = userRepository.findByEmailAndCompanyId("JaneDoe@trident.com", 2L);
    Map<String, User> map = Collections.emptyMap();

    public static class UserViewList {
        public List<UserView> getUserViews() {
            return userViews;
        }

        public void setUserViews(List<UserView> userViews) {
            this.userViews = userViews;
        }

        List<UserView> userViews;

        public UserViewList() {
            userViews = new ArrayList<>();
        }
    }

    @Test
    public void canGetUsers() {
        UserViewList response = restTemplate.postForObject("http://localhost:" + port + "/getUsers", vcvStaff, UserViewList.class);
        List<UserView> users = response.getUserViews();
        assertThat(users).isEqualTo(new UserView().build(testUsers));
    }

    @Test
    public void canChangeAdmin() {
        map.put("VCV", vcvStaff);
        map.put("Employee", newAdmin);

        MessageView.UserReport response = restTemplate.postForObject("http://localhost:" + port + "/changeAdmin", map, MessageView.UserReport.class);
        assertThat(response).isEqualTo(new MessageView.UserReport().build(newAdmin,"Successfully Changed Company Admins"));
    }
}
