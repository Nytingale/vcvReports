package com.vcv.backend.controllers;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.enums.JobType;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    String garageString = "CourtesyGarage";

    String baseURL;

    Company testGarage;
    List<Job> testJobs;

    Job newJob = new Job.Builder()
            .setId(5L)
            .setJobType(JobType.Accident)
            .setJobDate(Timestamp.valueOf(LocalDateTime.now()))
            .setJobCost(500)
            .setJobDetails("Broken windshield")
            .setVin("JMYSTC3A4U000993")
            .setCompanyId(2L)
            .build();

    Job updatedJob = new Job.Builder()
            .setId(5L)
            .setJobType(JobType.Accident)
            .setJobDate(Timestamp.valueOf(LocalDateTime.now().plusDays(5)))
            .setJobCost(700)
            .setJobDetails("Broken windshield and replaced a rim")
            .setVin("JMYSTC3A4U000993")
            .setCompanyId(2L)
            .build();

    Vehicle newVehicle = new Vehicle.Builder()
            .setVin("JMYSTC3A4U000993 ")
            .setYear(2004)
            .setMake("Mistubishi")
            .setModel("Lancer")
            .setValue(65000)
            .setMileage(8500)
            .setDealership("MQI")
            .setEvaluationDate(Timestamp.valueOf(LocalDateTime.now()))
            .setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()))
            .setNumAccidents(1)
            .setNumRobberies(0)
            .setNumSalvages(0)
            .setNumServices(0)
            .setNumOwners(1)
            .setPolicyNumber("D78FDG785563")
            .setInsuranceId(2L)
            .build();


    @Before
    public void setup() {
        testJobs = jobRepository.findByCompanyIdOrderByIdDesc(3L);

        testGarage = companyRepository.findById(3L).get();

        baseURL = "http://localhost:" + port + "/vcv/mechanic";
    }

    @Test
    public void canGetMechanicJobs() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getMechanicJobs?garage=" + garageString);
        ResponseEntity<JobView[]> response = restTemplate.getForEntity(uri, JobView[].class);
        assertThat(Arrays.equals(response.getBody(), new JobView().build(testJobs, testGarage, null).toArray())).isTrue();
    }

    @Test
    public void canAddJob() throws URISyntaxException {
        vehicleRepository.save(newVehicle);
        URI uri = new URI(baseURL + "/addJob");
        ResponseEntity<MessageView.JobReport> response = restTemplate.postForEntity(uri, newJob, MessageView.JobReport.class);
        assertThat(response.getBody().equals(new MessageView.JobReport().build(newJob, "Successfully Saved the Mechanic Job"))).isTrue();
    }

    @Test
    public void canUpdateJob() throws URISyntaxException  {
        URI uri = new URI(baseURL + "/updateJob");
        ResponseEntity<MessageView.JobReport> response = restTemplate.postForEntity(uri, updatedJob, MessageView.JobReport.class);
        assertThat(response.getBody().equals(new MessageView.JobReport().build(updatedJob, "Successfully Updated the Mechanic Job"))).isTrue();
        vehicleRepository.delete(newVehicle);
    }
}
