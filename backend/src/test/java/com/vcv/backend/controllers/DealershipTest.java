package com.vcv.backend.controllers;

import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.VehicleView;
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
public class DealershipTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private UserRepository userRepository;
    @Autowired private VehicleRepository vehicleRepository;

    List<Vehicle> testVehicles = vehicleRepository.findByDealershipOrderByRegistrationDateDesc("MQI");

    String dealershipString = "MQI";

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

    public static class VehicleViewList {
        List<VehicleView> vehicleViews;

        public VehicleViewList() {
            vehicleViews = new ArrayList<>();
        }

        public List<VehicleView> getVehicleViews() {
            return vehicleViews;
        }
    }

    @Test
    public void canGetRegisteredVehicles() {
        VehicleViewList response = restTemplate.getForObject("http://localhost:" + port + "/getRegisteredVehicles?dealership=" + dealershipString, VehicleViewList.class);
        assertThat(response.getVehicleViews()).isEqualTo(new VehicleView().build(testVehicles));
    }

    @Test
    public void canRegisterVehicle() {
        MessageView.Registration response = restTemplate.postForObject("http://localhost:" + port + "/registerVehicle", newVehicle, MessageView.Registration.class);
        assertThat(response).isEqualTo(new MessageView.Registration().build(newVehicle, "Successfully Registered Vehicle"));
    }
}
