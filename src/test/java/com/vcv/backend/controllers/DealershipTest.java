package com.vcv.backend.controllers;

import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.data.TestCompany;
import com.vcv.backend.data.TestVehicle;
import com.vcv.backend.repositories.CompanyRepository;
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
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DealershipTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private VehicleRepository vehicleRepository;
    
    private TestCompany testCompany = new TestCompany();
    private TestVehicle testVehicle = new TestVehicle();

    private String baseURL = "http://localhost:";
    

    @Before
    public void setup() {
        testVehicle.setVehicles(vehicleRepository.findByDealershipOrderByRegistrationDateDesc("MQI"));
        testCompany.setInsurance(companyRepository.findById(2L).get());
        testCompany.setInsuranceString("Trident_Insurance");

        baseURL += port + "/vcv/dealership";
    }

    @Test
    public void canGetRegisteredVehicles() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getRegisteredVehicles?dealership=" + testVehicle.getNewVehicle().getDealership());
        ResponseEntity<VehicleView[]> response = restTemplate.getForEntity(uri, VehicleView[].class);
        assertThat(Arrays.equals(response.getBody(), new VehicleView().build(testVehicle.getVehicles(), testCompany.getInsurance()).toArray())).isTrue();
    }

    @Test
    public void canRegisterVehicle() throws URISyntaxException {
        URI uri = new URI(baseURL + "/registerVehicle");
        ResponseEntity<MessageView.Registration> response = restTemplate.postForEntity(uri, testVehicle.getNewVehicle(), MessageView.Registration.class);
        assertThat(response.getBody().equals(new MessageView.Registration().build(testVehicle.getNewVehicle(), "Successfully Registered Vehicle"))).isTrue();

        vehicleRepository.delete(testVehicle.getNewVehicle());
    }
}
