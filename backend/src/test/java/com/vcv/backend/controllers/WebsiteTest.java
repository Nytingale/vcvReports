package com.vcv.backend.controllers;

import com.vcv.backend.Main;
import com.vcv.backend.entities.*;
import com.vcv.backend.repositories.*;

import com.vcv.backend.views.CompanyView;
import com.vcv.backend.views.VehicleView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

    String vinString = "JSAFTD02V00200457";
    String yearString = "2004";
    String makeString = "Suzuki";
    String modelString = "Grand Vitara";

    String baseURL;

    List<Job> testJobs;
    List<Claim> testClaims;
    List<Company> testCompanies;
    Vehicle testVehicle;

    public class CompanyViewList {
        List<CompanyView> companyViews;

        public CompanyViewList() {
            companyViews = new ArrayList<>();
        }
        public List<CompanyView> getCompanyViews() {
            return companyViews;
        }
    }

    @Before
    public void setup() {
        testJobs = jobRepository.findByVinOrderByIdDesc(vinString);
        testClaims = claimRepository.findByVinOrderByClaimDateDesc(vinString);
        testCompanies = (List<Company>) companyRepository.findAll();
        testVehicle = vehicleRepository.findByVin(vinString);

        baseURL = "http://localhost:" + port + "/vcv";
    }

    @Test
    public void canGetCompanies() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getCompanies");
        ResponseEntity<CompanyViewList> response = restTemplate.getForEntity(uri, CompanyViewList.class);
        assertThat(response.getBody().getCompanyViews()).isEqualTo(new CompanyView().build(testCompanies));
    }

    @Test
    public void canSearchVehicleByVin() throws URISyntaxException {
        URI uri = new URI(baseURL + "/searchForVehicle?vin=" + vinString);
        ResponseEntity<VehicleView.BasicReport> response = restTemplate.getForEntity(uri, VehicleView.BasicReport.class);
        assertThat(response.getBody()).isEqualTo(new VehicleView.BasicReport().build(testVehicle));
    }

    @Test
    public void canSearchVehicleByYearMakeModel() throws URISyntaxException {
        URI uri = new URI(baseURL + "/searchForVehicle?year=" + yearString + "&make=" + makeString + "&model=" + modelString);
        ResponseEntity<VehicleView.BasicReport> response = restTemplate.getForEntity(uri, VehicleView.BasicReport.class);
        assertThat(response.getBody()).isEqualTo(new VehicleView.BasicReport().build(testVehicle));
    }

    @Test
    public void canGeneerateReport() throws URISyntaxException {
        URI uri = new URI(baseURL + "/generateReport?vin=" + vinString);
        ResponseEntity<VehicleView.FullReport> response = restTemplate.getForEntity(uri, VehicleView.FullReport.class);
        assertThat(response.getBody()).isEqualTo(new VehicleView.FullReport().build(testVehicle, testClaims, testJobs));
    }
}
