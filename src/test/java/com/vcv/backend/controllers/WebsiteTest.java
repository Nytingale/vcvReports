package com.vcv.backend.controllers;

import com.vcv.backend.data.*;
import com.vcv.backend.repositories.*;
import com.vcv.backend.entities.Company;
import com.vcv.backend.exceptions.VehicleServiceException;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    private TestJob testJob = new TestJob();
    private TestClaim testClaim = new TestClaim();
    private TestCompany testCompany = new TestCompany();
    private TestVehicle testVehicle = new TestVehicle();

    private String baseURL = "http://localhost:";

    @Before
    public void setup() {
        testJob.setJobs(jobRepository.findByVinOrderByIdDesc("JSAFTD02V00200457"));
        testClaim.setClaims(claimRepository.findByVinOrderByClaimDateDesc("JSAFTD02V00200457"));
        testCompany.setInsurance(companyRepository.findById(2L).get());
        testCompany.setCompanies((List<Company>) companyRepository.findAll());
        testCompany.setGarages((List<Company>) companyRepository.findAllById(testJob.getJobs().stream().map(job -> job.getCompanyId()).distinct().collect(Collectors.toList())));
        testVehicle.setOldVehicle(vehicleRepository.findById("JSAFTD02V00200457").get());
        testVehicle.setModel("Grand Vitara");
        testVehicle.setMake("Suzuki");
        testVehicle.setYear("2004");

        baseURL += port + "/vcv";
    }

    @Test
    public void canGetCompanies() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getCompanies");
        ResponseEntity<CompanyView[]> response = restTemplate.getForEntity(uri, CompanyView[].class);
        assertThat(Arrays.equals(response.getBody(), new CompanyView().build(testCompany.getCompanies()).toArray())).isTrue();
    }

    @Test
    public void canSearchVehicleByVin() throws URISyntaxException {
        URI uri = new URI(baseURL + "/searchForVehicle?vin=" + testVehicle.getOldVehicle().getVin());
        ResponseEntity<VehicleView.BasicReport> response = restTemplate.getForEntity(uri, VehicleView.BasicReport.class);
        assertThat(response.getBody().equals(new VehicleView.BasicReport().build(testVehicle.getOldVehicle()))).isTrue();
    }

    @Test
    public void canSearchVehicleByYearMakeModel() throws URISyntaxException {
        URI uri = new URI(baseURL + "/searchForVehicle?year=" + testVehicle.getYear() + "&make=" + testVehicle.getMake() + "&model=" + testVehicle.getModel());
        ResponseEntity<VehicleView.BasicReport> response = restTemplate.getForEntity(uri, VehicleView.BasicReport.class);
        assertThat(response.getBody().equals(new VehicleView.BasicReport().build(testVehicle.getOldVehicle()))).isTrue();
    }

    @Test
    public void canGeneerateReport() throws URISyntaxException, VehicleServiceException {
        URI uri = new URI(baseURL + "/generateReport?vin=" + testVehicle.getOldVehicle().getVin());
        ResponseEntity<VehicleView.FullReport> response = restTemplate.getForEntity(uri, VehicleView.FullReport.class);
        assertThat(response.getBody().equals(new VehicleView.FullReport().build(testVehicle.getOldVehicle(), testCompany.getInsurance(), testJob.getJobs(), testCompany.getGarages(), testClaim.getClaims()))).isTrue();
    }
}
