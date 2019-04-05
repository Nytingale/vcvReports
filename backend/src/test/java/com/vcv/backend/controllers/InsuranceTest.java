package com.vcv.backend.controllers;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Policy;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.enums.ClaimType;
import com.vcv.backend.enums.PolicyType;
import com.vcv.backend.repositories.*;
import com.vcv.backend.views.ClaimView;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.PolicyView;
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
public class InsuranceTest {
    @LocalServerPort private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private UserRepository userRepository;
    @Autowired private ClaimRepository claimRepository;
    @Autowired private PolicyRepository policyRepository;
    @Autowired private VehicleRepository vehicleRepository;

    String jobId = "5";
    String vinString = "JSAFTD02V00200457";
    String claimString = "ISDFKG899RWEFAS";
    String companyString = "TridentInsurance";

    Policy testPolicy = policyRepository.findByVin(vinString);
    Vehicle testVehicle = vehicleRepository.findById(vinString).get();

    List<Claim> testClaims = claimRepository.findByCompanyIdOrderByClaimDateDesc(2L);
    List<Policy> testPolicies = policyRepository.findByCompanyIdOrderByPolicyDateDesc(2L);
    List<Vehicle> testVehicles = vehicleRepository.findByInsuranceIdOrderByRegistrationDateDesc(2L);

    Policy newPolicy = new Policy.Builder()
            .setCompanyId(2L)
            .setPolicyNumber("D78FDG785563")
            .setPolicyOwner("Bobby Reynolds")
            .setPolicyDate(Timestamp.valueOf(LocalDateTime.now()))
            .setPolicyType(PolicyType.THIRD_PARTY)
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
            .setClaimType(ClaimType.ACCIDENT)
            .setClaimDate(Timestamp.valueOf(LocalDateTime.now()))
            .setClaimDetails("Some text here")
            .setPolicyNumber("D78FDG785563")
            .setValue(500)
            .setVin("JMYSTC3A4U000993")
            .build();

    Claim updatedClaim = new Claim.Builder()
            .setCompanyId(2L)
            .setClaimNumber("ISDFKG899RWEFAS")
            .setClaimType(ClaimType.ACCIDENT)
            .setClaimDate(Timestamp.valueOf(LocalDateTime.now()))
            .setClaimDetails("Right. The windshield goofed")
            .setPolicyNumber("D78FDG785563")
            .setValue(700)
            .setVin("JMYSTC3A4U000993")
            .build();

    public static class ClaimViewList {
        List<ClaimView> claimViews;

        public ClaimViewList() {
            claimViews = new ArrayList<>();
        }

        public List<ClaimView> getClaimViews() {
            return claimViews;
        }
    }

    public static class PolicyViewList {
        List<PolicyView> policyViews;

        public PolicyViewList() {
            policyViews = new ArrayList<>();
        }

        public List<PolicyView> getPolicyViews() {
            return policyViews;
        }
    }

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
    public void canGetPolicy() {
        PolicyView response = restTemplate.getForObject("http://localhost:" + port + "/getPolicy?vin=" + vinString, PolicyView.class);
        assertThat(response).isEqualTo(new PolicyView().build(testPolicy));
    }

    @Test
    public void canGetInsurancePolices() {
        PolicyViewList response = restTemplate.getForObject("http://localhost:" + port + "/getInsurancePolicies?insurance=" + companyString, PolicyViewList.class);
        assertThat(response.getPolicyViews()).isEqualTo(new PolicyView().build(testPolicies));
    }

    @Test
    public void canGetInsuredVehicles() {
        VehicleViewList response = restTemplate.getForObject("http://localhost:" + port + "/getInsuredVehicles?insurance=" + companyString, VehicleViewList.class);
        assertThat(response.getVehicleViews()).isEqualTo(new VehicleView().build(testVehicles));
    }

    @Test
    public void canGetInsuranceClaims() {
        ClaimViewList response = restTemplate.getForObject("http://localhost:" + port + "/getInsuranceClaims?insurance=" + companyString, ClaimViewList.class);
        assertThat(response.getClaimViews()).isEqualTo(new ClaimView().build(testClaims));
    }

    @Test
    public void canAddPolicy() {
        vehicleRepository.save(newVehicle);
        MessageView.InsuranceReport response = restTemplate.postForObject("http://localhost:" + port + "/addPolicy", newPolicy, MessageView.InsuranceReport.class);
        assertThat(response).isEqualTo(new MessageView.InsuranceReport().build(newPolicy, "Successfully Added Policy"));
    }

    @Test
    public void canAddClaim() {
        MessageView.InsuranceReport response = restTemplate.postForObject("http://localhost:" + port + "/addClaim", newClaim, MessageView.InsuranceReport.class);
        assertThat(response).isEqualTo(new MessageView.InsuranceReport().build(newClaim, "Successfully Added Claim"));
    }

    @Test
    public void canUpdateClaim() {
        MessageView.InsuranceReport response = restTemplate.postForObject("http://localhost:" + port + "/updateClaim", updatedClaim, MessageView.InsuranceReport.class);
        assertThat(response).isEqualTo(new MessageView.InsuranceReport().build(updatedClaim, "Successfully Updated Claim"));
        vehicleRepository.delete(newVehicle);
    }

    @Test
    public void canReportStolen() {
        MessageView.StolenReport response = restTemplate.getForObject("http://localhost:" + port + "/reportStolen?vin=" + vinString, MessageView.StolenReport.class);
        assertThat(response).isEqualTo(new MessageView.StolenReport().build(testVehicle, "Successfully Reported Vehicle Stolen"));
    }

    @Test
    public void canReportAccident() {
        MessageView.AccidentReport response = restTemplate.getForObject("http://localhost:" + port + "/reportAccident?vin=" + vinString, MessageView.AccidentReport.class);
        assertThat(response).isEqualTo(new MessageView.AccidentReport().build(testVehicle, "Successfully Reported Vehicle Accident"));
    }

    @Test
    public void canReportRecovered() {
        MessageView.StolenReport response = restTemplate.getForObject("http://localhost:" + port + "/reportRecovered?vin=" + vinString, MessageView.StolenReport.class);
        assertThat(response).isEqualTo(new MessageView.StolenReport().build(testVehicle, "Successfully Reported Vehicle Recovered"));
    }

    @Test
    public void canReportWrittenOff() {
        MessageView.WriteOffReport response = restTemplate.getForObject("http://localhost:" + port + "/reportWrittenOff?vin=" + vinString, MessageView.WriteOffReport.class);
        assertThat(response).isEqualTo(new MessageView.WriteOffReport().build(testVehicle, "Successfully Reported Vehicle Recovered"));
    }

    @Test
    public void canReportSalvaged() {
        MessageView.SalvageReport response = restTemplate.getForObject("http://localhost:" + port + "/reportSalvaged?vin=" + vinString, MessageView.SalvageReport.class);
        assertThat(response).isEqualTo(new MessageView.SalvageReport().build(testVehicle, "Successfully Reported Vehicle Salvaged"));
    }

    @Test
    public void canLinkJobToClaim() {
        MessageView.InsuranceReport response = restTemplate.getForObject("http://localhost:" + port + "/linkJobToClaim?id=" + jobId + "&number=" + claimString + "&company=" + companyString, MessageView.InsuranceReport.class);
        assertThat(response).isEqualTo(new MessageView.InsuranceReport().build(newClaim, "Successfully Linked Job to Claim"));
    }
}
