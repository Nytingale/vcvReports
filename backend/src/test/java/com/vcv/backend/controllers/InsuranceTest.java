package com.vcv.backend.controllers;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Policy;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.enums.ClaimType;
import com.vcv.backend.enums.PolicyType;
import com.vcv.backend.repositories.*;
import com.vcv.backend.views.ClaimView;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.PolicyView;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InsuranceTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private UserRepository userRepository;
    @Autowired private ClaimRepository claimRepository;
    @Autowired private PolicyRepository policyRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private VehicleRepository vehicleRepository;

    String jobId = "5";
    String vinString = "JSAFTD02V00200457";
    String claimString = "ISDFKG899RWEFAS";
    String companyString = "TridentInsurance";

    String baseURL;

    Policy testPolicy;
    Vehicle testVehicle;
    Company testInsuranceCompany;

    List<Claim> testClaims;
    List<Policy> testPolicies;
    List<Vehicle> testVehicles;

    Policy newPolicy = new Policy.Builder()
            .setCompanyId(2L)
            .setPolicyNumber("D78FDG785563")
            .setPolicyOwner("Bobby Reynolds")
            .setPolicyDate(Timestamp.valueOf(LocalDateTime.now()))
            .setPolicyType(PolicyType.ThirdParty)
            .setFinancer("")
            .setValid(true)
            .setVin("JMYSTC3A4U000993 ")
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

    Claim newClaim = new Claim.Builder()
            .setCompanyId(2L)
            .setClaimNumber("ISDFKG899RWEFAS")
            .setClaimType(ClaimType.Accident)
            .setClaimDate(Timestamp.valueOf(LocalDateTime.now()))
            .setClaimDetails("Some text here")
            .setPolicyNumber("D78FDG785563")
            .setValue(500)
            .setVin("JMYSTC3A4U000993")
            .build();

    Claim updatedClaim = new Claim.Builder()
            .setCompanyId(2L)
            .setClaimNumber("ISDFKG899RWEFAS")
            .setClaimType(ClaimType.Accident)
            .setClaimDate(Timestamp.valueOf(LocalDateTime.now()))
            .setClaimDetails("Right. The windshield goofed")
            .setPolicyNumber("D78FDG785563")
            .setValue(700)
            .setVin("JMYSTC3A4U000993")
            .build();

    @Before
    public void setup() {
        testPolicy = policyRepository.findByVin(vinString);
        testVehicle = vehicleRepository.findById(vinString).get();

        testInsuranceCompany = companyRepository.findById(2L).get();

        testClaims = claimRepository.findByCompanyIdOrderByClaimDateDesc(2L);
        testPolicies = policyRepository.findByCompanyIdOrderByPolicyDateDesc(2L);
        testVehicles = vehicleRepository.findByInsuranceIdOrderByRegistrationDateDesc(2L);

        baseURL = "http://localhost:" + port + "/vcv/insurance";
    }

    @Test
    public void canGetPolicy() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getPolicy?vin=" + vinString);
        ResponseEntity<PolicyView> response = restTemplate.getForEntity(uri, PolicyView.class);
        assertThat(response.getBody().equals(new PolicyView().build(testPolicy, testInsuranceCompany))).isTrue();
    }

    @Test
    public void canGetInsurancePolices() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getInsurancePolicies?insurance=" + companyString);
        ResponseEntity<PolicyView[]> response = restTemplate.getForEntity(uri, PolicyView[].class);
        assertThat(Arrays.equals(response.getBody(), new PolicyView().build(testPolicies, testInsuranceCompany).toArray())).isTrue();
    }

    @Test
    public void canGetInsuredVehicles() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getInsuredVehicles?insurance=" + companyString);
        ResponseEntity<VehicleView[]> response = restTemplate.getForEntity(uri, VehicleView[].class);
        assertThat(Arrays.equals(response.getBody(), new VehicleView().build(testVehicles, testInsuranceCompany).toArray())).isTrue();
    }

    @Test
    public void canGetInsuranceClaims() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getInsuranceClaims?insurance=" + companyString);
        ResponseEntity<ClaimView[]> response = restTemplate.getForEntity(uri, ClaimView[].class);
        assertThat(Arrays.equals(response.getBody(), new ClaimView().build(testClaims, testInsuranceCompany).toArray())).isTrue();
    }

    @Test
    public void canAddPolicy() throws URISyntaxException {
        vehicleRepository.save(newVehicle);
        URI uri = new URI(baseURL + "/addPolicy");
        ResponseEntity<MessageView.InsuranceReport> response = restTemplate.postForEntity(uri, newPolicy, MessageView.InsuranceReport.class);
        assertThat(response.getBody().equals(new MessageView.InsuranceReport().build(newPolicy, testInsuranceCompany, "Successfully Added Policy"))).isTrue();
    }

    @Test
    public void canAddClaim() throws URISyntaxException {
        URI uri = new URI(baseURL + "/addClaim");
        ResponseEntity<MessageView.InsuranceReport> response = restTemplate.postForEntity(uri, newClaim, MessageView.InsuranceReport.class);
        assertThat(response.getBody().equals(new MessageView.InsuranceReport().build(newClaim, testInsuranceCompany, "Successfully Added Claim"))).isTrue();
    }

    @Test
    public void canUpdateClaim() throws URISyntaxException {
        URI uri = new URI(baseURL + "/updateClaim");
        ResponseEntity<MessageView.InsuranceReport> response = restTemplate.postForEntity(uri, updatedClaim, MessageView.InsuranceReport.class);
        assertThat(response.getBody().equals(new MessageView.InsuranceReport().build(updatedClaim, testInsuranceCompany, "Successfully Updated Claim"))).isTrue();
        vehicleRepository.delete(newVehicle);
    }

    @Test
    public void canReportStolen() throws URISyntaxException {
        URI uri = new URI(baseURL + "/reportStolen?vin=" + vinString);
        ResponseEntity<MessageView.StolenReport>  response = restTemplate.getForEntity(uri, MessageView.StolenReport.class);
        assertThat(response.getBody().equals(new MessageView.StolenReport().build(testVehicle, "Successfully Reported Vehicle Stolen"))).isTrue();
    }

    @Test
    public void canReportAccident() throws URISyntaxException {
        URI uri = new URI(baseURL + "/reportAccident?vin=" + vinString);
        ResponseEntity<MessageView.AccidentReport> response = restTemplate.getForEntity(uri, MessageView.AccidentReport.class);
        assertThat(response.getBody().equals(new MessageView.AccidentReport().build(testVehicle, "Successfully Reported Vehicle Accident"))).isTrue();
    }

    @Test
    public void canReportRecovered() throws URISyntaxException {
        URI uri = new URI(baseURL + "/reportRecovered?vin=" + vinString);
        ResponseEntity<MessageView.StolenReport>  response = restTemplate.getForEntity(uri, MessageView.StolenReport.class);
        assertThat(response.getBody().equals(new MessageView.StolenReport().build(testVehicle, "Successfully Reported Vehicle Recovered"))).isTrue();
    }

    @Test
    public void canReportWrittenOff() throws URISyntaxException {
        URI uri = new URI(baseURL + "/reportWrittenOff?vin=" + vinString);
        ResponseEntity<MessageView.WriteOffReport> response = restTemplate.getForEntity(uri, MessageView.WriteOffReport.class);
        assertThat(response.getBody().equals(new MessageView.WriteOffReport().build(testVehicle, "Successfully Reported Vehicle Recovered"))).isTrue();
    }

    @Test
    public void canReportSalvaged() throws URISyntaxException {
        URI uri = new URI(baseURL + "/reportSalvaged?vin=" + vinString);
        ResponseEntity<MessageView.SalvageReport> response = restTemplate.getForEntity(uri, MessageView.SalvageReport.class);
        assertThat(response.getBody().equals(new MessageView.SalvageReport().build(testVehicle, "Successfully Reported Vehicle Salvaged"))).isTrue();
    }

    @Test
    public void canLinkJobToClaim() throws URISyntaxException {
        URI uri = new URI(baseURL + "/linkJobToClaim?id=" + jobId + "&number=" + claimString + "&company=" + companyString);
        ResponseEntity<MessageView.InsuranceReport> response = restTemplate.getForEntity(uri, MessageView.InsuranceReport.class);
        assertThat(response.getBody().equals(new MessageView.InsuranceReport().build(newClaim, testInsuranceCompany, "Successfully Linked Job to Claim"))).isTrue();
    }
}
