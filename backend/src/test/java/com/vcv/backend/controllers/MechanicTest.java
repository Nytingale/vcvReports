package com.vcv.backend.controllers;

import com.vcv.backend.data.TestCompany;
import com.vcv.backend.data.TestJob;
import com.vcv.backend.data.TestVehicle;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.JobRepository;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.views.JobView;
import com.vcv.backend.views.MessageView;
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
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MechanicTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private JobRepository jobRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private VehicleRepository vehicleRepository;

    private TestJob testJob = new TestJob();
    private TestCompany testCompany = new TestCompany();
    private TestVehicle testVehicle = new TestVehicle();

    private String baseURL = "http://localhost:";

    @Before
    public void setup() {
        testJob.setJobs(jobRepository.findByCompanyIdOrderByIdDesc(3L));
        testCompany.setGarage(companyRepository.findById(3L).get());
        testCompany.setGarageString("Courtesy_Garage");

        baseURL += port + "/vcv/mechanic";
    }

    @Test
    public void canGetMechanicJobs() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getMechanicJobs?garage=" + testCompany.getGarageString());
        ResponseEntity<JobView[]> response = restTemplate.getForEntity(uri, JobView[].class);
        assertThat(Arrays.equals(response.getBody(), new JobView().build(testJob.getJobs(), testCompany.getGarage(), null).toArray())).isTrue();
    }

    @Test
    public void canAddJob() throws URISyntaxException {
        vehicleRepository.save(testVehicle.getNewVehicle());

        URI uri = new URI(baseURL + "/addJob");
        ResponseEntity<MessageView.JobReport> response = restTemplate.postForEntity(uri, testJob.getNewJob(), MessageView.JobReport.class);
        assertThat(response.getBody().equals(new MessageView.JobReport().build(testJob.getNewJob(), "Successfully Saved the Mechanic Job"))).isTrue();

        jobRepository.delete(testJob.getNewJob());
        vehicleRepository.delete(testVehicle.getNewVehicle());
    }

    @Test
    public void canUpdateJob() throws URISyntaxException  {
        vehicleRepository.save(testVehicle.getNewVehicle());
        jobRepository.save(testJob.getNewJob());

        URI uri = new URI(baseURL + "/updateJob");
        ResponseEntity<MessageView.JobReport> response = restTemplate.postForEntity(uri, testJob.getUpdatedJob(), MessageView.JobReport.class);
        assertThat(response.getBody().equals(new MessageView.JobReport().build(testJob.getUpdatedJob(), "Successfully Updated the Mechanic Job"))).isTrue();

        jobRepository.delete(testJob.getUpdatedJob());
        vehicleRepository.delete(testVehicle.getNewVehicle());
    }
}
