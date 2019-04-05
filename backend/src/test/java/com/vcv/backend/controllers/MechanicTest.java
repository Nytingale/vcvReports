package com.vcv.backend.controllers;

import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.enums.JobType;
import com.vcv.backend.repositories.JobRepository;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.views.JobView;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.UserView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MechanicTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private JobRepository jobRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private VehicleRepository vehicleRepository;

    String garageString = "CourtesyGarage";

    Job newJob = new Job.Builder()
            .setId(5L)
            .setJobType(JobType.ACCIDENT)
            .setJobDate(Timestamp.valueOf(LocalDateTime.now()))
            .setJobCost(500)
            .setJobDetails("Broken windshield")
            .setVin("JMYSTC3A4U000993")
            .setCompanyId(2L)
            .build();

    Job updatedJob = new Job.Builder()
            .setId(5L)
            .setJobType(JobType.ACCIDENT)
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

    List<Job> testJobs = jobRepository.findByCompanyIdOrderByJobIdDesc(3L);

    public static class JobViewList {
        List<JobView> jobViews;

        public JobViewList() {
            jobViews = new ArrayList<>();
        }

        public List<JobView> getJobViews() {
            return jobViews;
        }
    }

    @Test
    public void canGetMechanicJobs() {
        JobViewList response = restTemplate.getForObject("http://localhost:" + port + "/getMechanicJobs?garage=" + garageString, JobViewList.class);
        assertThat(response.getJobViews()).isEqualTo(new JobView().build(testJobs));
    }

    @Test
    public void canAddJob() {
        vehicleRepository.save(newVehicle);
        MessageView.JobReport response = restTemplate.postForObject("http://localhost:" + port + "/addJob", newJob, MessageView.JobReport.class);
        assertThat(response).isEqualTo(new MessageView.JobReport().build(newJob, "Successfully Saved the Mechanic Job"));
    }

    @Test
    public void canUpdateJob() {
        MessageView.JobReport response = restTemplate.postForObject("http://localhost:" + port + "/updateJob", updatedJob, MessageView.JobReport.class);
        assertThat(response).isEqualTo(new MessageView.JobReport().build(updatedJob, "Successfully Updated the Mechanic Job"));
        vehicleRepository.delete(newVehicle);
    }
}
