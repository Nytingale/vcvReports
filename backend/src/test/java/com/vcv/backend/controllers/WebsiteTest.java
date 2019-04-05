package com.vcv.backend.controllers;

import com.vcv.backend.entities.*;
import com.vcv.backend.repositories.*;

import com.vcv.backend.views.CompanyView;
import com.vcv.backend.views.VehicleView;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebsiteTest {

    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private JobRepository jobRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ClaimRepository claimRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private VehicleRepository vehicleRepository;

    String vinString = "JSZYA215195102215";
    String yearString = "2012";
    String makeString = "Isuzu";
    String modelString = "D-Max";
    String companyString = "TridentInsurance";

    List<Job> testJobs = jobRepository.findByVinOrderByJobDateDesc(vinString);
    List<Claim> testClaims = claimRepository.findByVinOrderByClaimDateDesc(vinString);
    Company testCompany = companyRepository.findByCompanyName(companyString);
    Vehicle testVehicle = vehicleRepository.findByVin(vinString);

    @Test
    public void canGetWebsite() {
        CompanyView response = restTemplate.getForObject("http://localhost:" + port + "/getWebsite?company=" + companyString, CompanyView.class);
        assertThat(response).isEqualTo(new CompanyView().build(testCompany));
    }

    @Test
    public void canSearchVehicleByVin() {
        VehicleView.BasicReport response = restTemplate.getForObject("http://localhost:" + port + "/searchForVehicle?vinString=" + vinString, VehicleView.BasicReport.class);
        assertThat(response).isEqualTo(new VehicleView.BasicReport().build(testVehicle));
    }

    @Test
    public void canSearchVehicleByYearMakeModel() {
        VehicleView.BasicReport response = restTemplate.getForObject("http://localhost:" + port + "/searchForVehicle?yearString=" + yearString + "&makeString=" + makeString + "&modelString=" + modelString, VehicleView.BasicReport.class);
        assertThat(response).isEqualTo(new VehicleView.BasicReport().build(testVehicle));
    }

    @Test
    public void canGeneerateReport() {
        VehicleView.FullReport response = restTemplate.getForObject("http://localhost:" + port + "/generateReport?vinString=" + vinString, VehicleView.FullReport.class);
        assertThat(response).isEqualTo(new VehicleView.FullReport().build(testVehicle, testClaims, testJobs));
    }
}
