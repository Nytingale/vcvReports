package com.vcv.backend.controllers;

import com.vcv.backend.entities.*;
import com.vcv.backend.repositories.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(Website.class)
public class WebsiteTest {
    @Autowired private MockMvc mvc;

    @MockBean private JobRepository jobRepo;
    @MockBean private UserRepository userRepo;
    @MockBean private ClaimRepository claimRepo;
    @MockBean private CompanyRepository companyRepo;
    @MockBean private VehicleRepository vehicleRepo;

    private JacksonTester<Job> jsonJob;
    private JacksonTester<User> jsonUser;
    private JacksonTester<Claim> jsonClaim;
    private JacksonTester<Company> jsonCompany;
    private JacksonTester<Vehicle> jsonVehicle;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void canGetWebsite() {

    }
}
