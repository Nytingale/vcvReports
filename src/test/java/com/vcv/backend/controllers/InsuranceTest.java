package com.vcv.backend.controllers;

import com.vcv.backend.data.*;
import com.vcv.backend.views.*;
import com.vcv.backend.repositories.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.http.ResponseEntity;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

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
    

    private TestJob testJob = new TestJob();
    private TestClaim testClaim = new TestClaim();
    private TestPolicy testPolicy = new TestPolicy();
    private TestCompany testCompany = new TestCompany();
    private TestVehicle testVehicle = new TestVehicle();

    private String baseURL = "http://localhost:";
    
    @Before
    public void setup() {
        testJob.setId("4");
        testPolicy.setOldPolicy(policyRepository.findByVinAndValid("JSAFTD02V00200457", true));
        testVehicle.setOldVehicle(vehicleRepository.findById("JSAFTD02V00200457").get());
        testCompany.setInsurance(companyRepository.findById(2L).get());
        testCompany.setInsuranceString("Trident_Insurance");

        testClaim.setClaims(claimRepository.findByCompanyIdOrderByClaimDateDesc(2L));
        testPolicy.setPolicies(policyRepository.findByCompanyIdOrderByPolicyDateDesc(2L));
        testVehicle.setVehicles(vehicleRepository.findByInsuranceIdOrderByRegistrationDateDesc(2L));

        baseURL += port + "/vcv/insurance";
    }

    @Test
    public void canGetPolicy() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getPolicy?vin=" + testVehicle.getOldVehicle().getVin());
        ResponseEntity<PolicyView> response = restTemplate.getForEntity(uri, PolicyView.class);
        assertThat(response.getBody().equals(new PolicyView().build(testPolicy.getOldPolicy(), testCompany.getInsurance()))).isTrue();
    }

    @Test
    public void canGetInsurancePolices() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getInsurancePolicies?insurance=" + testCompany.getInsuranceString());
        ResponseEntity<PolicyView[]> response = restTemplate.getForEntity(uri, PolicyView[].class);
        assertThat(Arrays.equals(response.getBody(), new PolicyView().build(testPolicy.getPolicies(), testCompany.getInsurance()).toArray())).isTrue();
    }

    @Test
    public void canGetInsuredVehicles() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getInsuredVehicles?insurance=" + testCompany.getInsuranceString());
        ResponseEntity<VehicleView[]> response = restTemplate.getForEntity(uri, VehicleView[].class);
        assertThat(Arrays.equals(response.getBody(), new VehicleView().build(testVehicle.getVehicles(), testCompany.getInsurance()).toArray())).isTrue();
    }

    @Test
    public void canGetInsuranceClaims() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getInsuranceClaims?insurance=" + testCompany.getInsuranceString());
        ResponseEntity<ClaimView[]> response = restTemplate.getForEntity(uri, ClaimView[].class);
        assertThat(Arrays.equals(response.getBody(), new ClaimView().build(testClaim.getClaims(), testCompany.getInsurance()).toArray())).isTrue();
    }

    @Test
    public void canAddPolicy() throws URISyntaxException {
        vehicleRepository.save(testVehicle.getNewVehicle());

        URI uri = new URI(baseURL + "/add");
        ResponseEntity<MessageView.InsuranceReport> response = restTemplate.postForEntity(uri, testPolicy.getNewPolicy(), MessageView.InsuranceReport.class);
        assertThat(response.getBody().equals(new MessageView.InsuranceReport().build(testPolicy.getNewPolicy(), testCompany.getInsurance(), "Successfully Added Policy"))).isTrue();

        policyRepository.delete(testPolicy.getNewPolicy());
        vehicleRepository.delete(testVehicle.getNewVehicle());
    }

    @Test
    public void canAddClaim() throws URISyntaxException {
        vehicleRepository.save(testVehicle.getNewVehicle());
        policyRepository.save(testPolicy.getNewPolicy());

        URI uri = new URI(baseURL + "/add");
        ResponseEntity<MessageView.InsuranceReport> response = restTemplate.postForEntity(uri, testClaim.getNewClaim(), MessageView.InsuranceReport.class);
        assertThat(response.getBody().equals(new MessageView.InsuranceReport().build(testClaim.getNewClaim(), testCompany.getInsurance(), "Successfully Added Claim"))).isTrue();

        claimRepository.delete(testClaim.getNewClaim());
        policyRepository.delete(testPolicy.getNewPolicy());
        vehicleRepository.delete(testVehicle.getNewVehicle());
    }

    @Test
    public void canUpdateClaim() throws URISyntaxException {
        vehicleRepository.save(testVehicle.getNewVehicle());
        policyRepository.save(testPolicy.getNewPolicy());
        claimRepository.save(testClaim.getNewClaim());

        URI uri = new URI(baseURL + "/update");
        ResponseEntity<MessageView.InsuranceReport> response = restTemplate.postForEntity(uri, testClaim.getUpdatedClaim(), MessageView.InsuranceReport.class);
        assertThat(response.getBody().equals(new MessageView.InsuranceReport().build(testClaim.getUpdatedClaim(), testCompany.getInsurance(), "Successfully Updated Claim"))).isTrue();

        claimRepository.delete(testClaim.getUpdatedClaim());
        policyRepository.delete(testPolicy.getNewPolicy());
        vehicleRepository.delete(testVehicle.getNewVehicle());
    }

    @Test
    public void canReportStolen() throws URISyntaxException {
        testVehicle.getOldVehicle().setStolen(true);

        URI uri = new URI(baseURL + "/reportStolen?vin=" + testVehicle.getOldVehicle().getVin());
        ResponseEntity<MessageView.StolenReport>  response = restTemplate.getForEntity(uri, MessageView.StolenReport.class);
        assertThat(response.getBody().equals(new MessageView.StolenReport().build(testVehicle.getOldVehicle(), "Successfully Reported Vehicle Stolen"))).isTrue();

        testVehicle.getOldVehicle().setStolen(false);
    }

    @Test
    public void canReportAccident() throws URISyntaxException {
        testVehicle.getOldVehicle().setNumAccidents(testVehicle.getOldVehicle().getNumAccidents() + 1);

        URI uri = new URI(baseURL + "/reportAccident?vin=" + testVehicle.getOldVehicle().getVin());
        ResponseEntity<MessageView.AccidentReport> response = restTemplate.getForEntity(uri, MessageView.AccidentReport.class);
        assertThat(response.getBody().equals(new MessageView.AccidentReport().build(testVehicle.getOldVehicle(), "Successfully Reported Vehicle Accident"))).isTrue();

        testVehicle.getOldVehicle().setNumAccidents(testVehicle.getOldVehicle().getNumAccidents() - 1);
    }

    @Test
    public void canReportRecovered() throws URISyntaxException {
        testVehicle.getOldVehicle().setStolen(false);

        URI uri = new URI(baseURL + "/reportRecovered?vin=" + testVehicle.getOldVehicle().getVin());
        ResponseEntity<MessageView.StolenReport>  response = restTemplate.getForEntity(uri, MessageView.StolenReport.class);
        assertThat(response.getBody().equals(new MessageView.StolenReport().build(testVehicle.getOldVehicle(), "Successfully Reported Vehicle Recovered"))).isTrue();

        testVehicle.getOldVehicle().setStolen(true);
    }

    @Test
    public void canReportWrittenOff() throws URISyntaxException {
        testVehicle.getOldVehicle().setWrittenOff(true);

        URI uri = new URI(baseURL + "/reportWrittenOff?vin=" + testVehicle.getOldVehicle().getVin());
        ResponseEntity<MessageView.WriteOffReport> response = restTemplate.getForEntity(uri, MessageView.WriteOffReport.class);
        assertThat(response.getBody().equals(new MessageView.WriteOffReport().build(testVehicle.getOldVehicle(), "Successfully Written Off Vehicle"))).isTrue();

        testVehicle.getOldVehicle().setWrittenOff(false);
    }

    @Test
    public void canReportSalvaged() throws URISyntaxException {
        testVehicle.getOldVehicle().setNumSalvages(testVehicle.getOldVehicle().getNumSalvages() + 1);

        URI uri = new URI(baseURL + "/reportSalvaged?vin=" + testVehicle.getOldVehicle().getVin());
        ResponseEntity<MessageView.SalvageReport> response = restTemplate.getForEntity(uri, MessageView.SalvageReport.class);
        assertThat(response.getBody().equals(new MessageView.SalvageReport().build(testVehicle.getOldVehicle(), "Successfully Reported Vehicle Salvaged"))).isTrue();

        testVehicle.getOldVehicle().setNumSalvages(testVehicle.getOldVehicle().getNumSalvages() - 1);
    }

    @Test
    public void canLinkJobToClaim() throws URISyntaxException {
        vehicleRepository.save(testVehicle.getNewVehicle());
        policyRepository.save(testPolicy.getNewPolicy());
        claimRepository.save(testClaim.getNewClaim());

        URI uri = new URI(baseURL + "/link?id=" + testJob.getId() + "&number=" + testClaim.getNewClaim().getClaimNumber() + "&company=" + testCompany.getInsuranceString());
        ResponseEntity<MessageView.InsuranceReport> response = restTemplate.getForEntity(uri, MessageView.InsuranceReport.class);
        assertThat(response.getBody().equals(new MessageView.InsuranceReport().build(testClaim.getNewClaim(), testCompany.getInsurance(), "Successfully Linked TestJob to Claim"))).isTrue();

        claimRepository.delete(testClaim.getNewClaim());
        policyRepository.delete(testPolicy.getNewPolicy());
        vehicleRepository.delete(testVehicle.getNewVehicle());
    }
}
