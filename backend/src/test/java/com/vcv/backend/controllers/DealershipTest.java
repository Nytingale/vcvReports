package com.vcv.backend.controllers;

import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.views.MessageView;
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

    String dealershipString = "MQI";

    String baseURL;

    List<Vehicle> testVehicles;

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

    public class VehicleViewList {
        List<VehicleView> vehicleViews;

        public VehicleViewList() {
            vehicleViews = new ArrayList<>();
        }
        public List<VehicleView> getVehicleViews() {
            return vehicleViews;
        }
    }

    @Before
    public void setup() {
        testVehicles = vehicleRepository.findByDealershipOrderByRegistrationDateDesc(dealershipString);

        baseURL = "http://localhost:" + port + "/vcv/dealership";
    }

    @Test
    public void canGetRegisteredVehicles() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getRegisteredVehicles?dealership=" + dealershipString);
        ResponseEntity<VehicleViewList> response = restTemplate.getForEntity(uri, VehicleViewList.class);
        assertThat(response.getBody().getVehicleViews()).isEqualTo(new VehicleView().build(testVehicles));
    }

    @Test
    public void canRegisterVehicle() throws URISyntaxException {
        URI uri = new URI(baseURL + "/registerVehicle");
        ResponseEntity<MessageView.Registration> response = restTemplate.postForEntity(uri, newVehicle, MessageView.Registration.class);
        assertThat(response.getBody()).isEqualTo(new MessageView.Registration().build(newVehicle, "Successfully Registered Vehicle"));
    }
}
