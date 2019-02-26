package com.vcv.backend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Policies")
@IdClass(Policy.CompositeKey.class)
public class Policy {
    @Id private String companyName;
    @Id private String policyNumber;

    private String policyOwner;
    private String policyName;
    private String policyDate;
    private String financer;
    private Boolean valid;
    private String vin;

    public String getCompanyName() {
        return companyName;
    }
    public String getPolicyNumber() {
        return policyNumber;
    }
    public String getPolicyOwner() {
        return policyOwner;
    }
    public String getPolicyName() {
        return policyName;
    }
    public String getPolicyDate() {
        return policyDate;
    }
    public String getFinancer() {
        return financer;
    }
    public Boolean isValid() {
        return valid;
    }
    public String getVin() {
        return vin;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
    public void setPolicyOwner(String policyOwner) {
        this.policyOwner = policyOwner;
    }
    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }
    public void setPolicyDate(String policyDate) {
        this.policyDate = policyDate;
    }
    public void setFinancer(String financer) {
        this.financer = financer;
    }
    public void setValid(Boolean valid) {
        this.valid = valid;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, policyNumber, policyOwner, policyName, policyDate, financer, valid, vin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Policy)) return false;
        Policy policy = (Policy) o;
        return valid == policy.valid &&
                companyName.equals(policy.companyName) &&
                policyNumber.equals(policy.policyNumber) &&
                policyOwner.equals(policy.policyOwner) &&
                policyName.equals(policy.policyName) &&
                policyDate.equals(policy.policyDate) &&
                Objects.equals(financer, policy.financer) &&
                vin.equals(policy.vin);
    }

    public class CompositeKey implements Serializable {
        private String companyName;
        private String policyNumber;

        CompositeKey() {

        }
    }
}
