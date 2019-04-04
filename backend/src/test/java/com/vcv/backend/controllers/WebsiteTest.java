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

    private String vin = "MPATFS85HCT102885";
    private String year = "2012";
    private String make = "Isuzu";
    private String model = "D-Max";
    private String companyName = "TridentInsurance";

    private List<Job> testJobs = jobRepository.findByVinOrderByJobDateDesc(vin);
    private List<Claim> testClaims = claimRepository.findByVinOrderByClaimDateDesc(vin);
    private Company testCompany = companyRepository.findByCompanyName(companyName);
    private Vehicle testVehicle = vehicleRepository.findByVin(vin);

    @Test
    public void canGetWebsite() {
        CompanyView response = restTemplate.getForObject("http://localhost:" + port + "/getWebsite?testCompany=" + companyName, CompanyView.class);
        assertThat(response).isEqualTo(new CompanyView().build(testCompany));
    }

    @Test
    public void canSearchVehicleByVin() {
        VehicleView.BasicReport response = restTemplate.getForObject("http://localhost:" + port + "/searchForVehicle?vin=" + vin, VehicleView.BasicReport.class);
        assertThat(response).isEqualTo(new VehicleView.BasicReport().build(testVehicle));
    }

    @Test
    public void canSearchVehicleByYearMakeModel() {
        VehicleView.BasicReport response = restTemplate.getForObject("http://localhost:" + port + "/searchForVehicle?year=" + year + "&make=" + make + "&model=" + model, VehicleView.BasicReport.class);
        assertThat(response).isEqualTo(new VehicleView.BasicReport().build(testVehicle));
    }

    @Test
    public void canGeneerateReport() {
        VehicleView.FullReport response = restTemplate.getForObject("http://localhost:" + port + "/generateReport?vin=" + vin, VehicleView.FullReport.class);
        assertThat(response).isEqualTo(new VehicleView.FullReport().build(testVehicle, testClaims, testJobs));
    }
}
