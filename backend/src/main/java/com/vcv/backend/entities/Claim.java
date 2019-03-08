package com.vcv.backend.entities;

import com.vcv.backend.enums.ClaimType;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.IdClass;

import java.util.Objects;

import java.sql.Timestamp;

import java.io.Serializable;

@Entity
@Table(name = "Claims")
@IdClass(Claim.CompositeKey.class)
public class Claim {
    @Id private String claimNumber;
    @Id private String companyName;

    private ClaimType claimType;
    private Timestamp claimDate;
    private String claimDetails;
    private String policyNumber;
    private Integer value;
    private String vin;
    private Long jobId;

    public String getClaimNumber() {
        return claimNumber;
    }
    public String getCompanyName() {
        return companyName;
    }
    public ClaimType getClaimType() {
        return claimType;
    }
    public Timestamp getClaimDate() {
        return claimDate;
    }
    public String getClaimDetails() {
        return claimDetails;
    }
    public String getPolicyNumber() {
        return policyNumber;
    }
    public Integer getValue() {
        return value;
    }
    public Long getJobId() {
        return jobId;
    }
    public String getVin() {
        return vin;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public void setClaimType(ClaimType claimType) {
        this.claimType = claimType;
    }
    public void setClaimDate(Timestamp claimDate) {
        this.claimDate = claimDate;
    }
    public void setClaimDetails(String claimDetails) {
        this.claimDetails = claimDetails;
    }
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(claimNumber, companyName, claimType, claimDate, claimDetails, policyNumber, value, jobId, vin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Claim)) return false;
        Claim claim = (Claim) o;
        return claimNumber.equals(claim.claimNumber) &&
                companyName.equals(claim.companyName) &&
                claimType == claim.claimType &&
                claimDate.equals(claim.claimDate) &&
                claimDetails.equals(claim.claimDetails) &&
                policyNumber.equals(claim.policyNumber) &&
                value == claim.value &&
                jobId == claim.jobId &&
                vin.equals(claim.vin);
    }

    public static class CompositeKey implements Serializable {
        private String claimNumber;
        private String companyName;

        CompositeKey() {}

        public String getClaimNumber() {
            return claimNumber;
        }
        public String getCompanyName() {
            return companyName;
        }
        public void setClaimNumber(String claimNumber) {
            this.claimNumber = claimNumber;
        }
        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}
