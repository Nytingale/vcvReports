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
@Table(name = "Claim")
@IdClass(Claim.CompositeKey.class)
public class Claim {
    @Id private Long companyId;
    @Id private String claimNumber;

    private ClaimType claimType;
    private Timestamp claimDate;
    private String claimDetails;
    private String policyNumber;
    private Integer value;
    private String vin;
    private Long jobId;

    public Long getCompanyId() {
        return companyId;
    }
    public String getClaimNumber() {
        return claimNumber;
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

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
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
        return Objects.hash(claimNumber, companyId, claimType, claimDate, claimDetails, policyNumber, value, jobId, vin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Claim)) return false;
        Claim claim = (Claim) o;
        return claimNumber.equals(claim.claimNumber) &&
                companyId.equals(claim.companyId) &&
                claimType == claim.claimType &&
                claimDate.equals(claim.claimDate) &&
                claimDetails.equals(claim.claimDetails) &&
                policyNumber.equals(claim.policyNumber) &&
                value == claim.value &&
                jobId == claim.jobId &&
                vin.equals(claim.vin);
    }

    public static class CompositeKey implements Serializable {
        private Long companyId;
        private String claimNumber;

        public CompositeKey() {}
        public CompositeKey(String claimNumber, Long companyId) {
            this.claimNumber = claimNumber;
            this.companyId = companyId;
        }

        public Long getCompanyId() {
            return companyId;
        }
        public String getClaimNumber() {
            return claimNumber;
        }
        public void setCompanyId(Long companyId) {
            this.companyId = companyId;
        }
        public void setClaimNumber(String claimNumber) {
            this.claimNumber = claimNumber;
        }
    }
}
