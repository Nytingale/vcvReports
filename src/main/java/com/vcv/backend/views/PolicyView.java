package com.vcv.backend.views;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Policy;
import com.vcv.backend.enums.PolicyType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PolicyView implements Serializable {
    private Long companyId;
    private String company;
    private String policyNumber;
    private String date;
    private String type;
    private String owner;
    private PolicyType pType;
    private Timestamp timestamp;
    private String financer;
    private Boolean valid;
    private String vin;

    public Long getCompanyId() {
        return companyId;
    }
    public String getCompany() {
        return company;
    }
    public String getPolicyNumber() {
        return policyNumber;
    }
    public String getDate() {
        return date;
    }
    public String getType() {
        return type;
    }
    public String getOwner() {
        return owner;
    }
    public PolicyType getPType() {
        return pType;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public String getFinancer() {
        return financer;
    }
    public Boolean getValid() {
        return valid;
    }
    public String getVin() {
        return vin;
    }

    public PolicyView setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }
    public PolicyView setCompany(String company) {
        this.company = company;
        return this;
    }
    public PolicyView setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
        return this;
    }
    public PolicyView setDate(String date) {
        this.date = date;
        return this;
    }
    public PolicyView setType(String type) {
        this.type = type;
        return this;
    }
    public PolicyView setOwner(String owner) {
        this.owner = owner;
        return this;
    }
    public PolicyView setPType(PolicyType pType) {
        this.pType = pType;
        return this;
    }
    public PolicyView setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }
    public PolicyView setFinancer(String financer) {
        this.financer = financer;
        return this;
    }
    public PolicyView setValid(Boolean valid) {
        this.valid = valid;
        return this;
    }
    public PolicyView setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public PolicyView() {}
    public PolicyView build(Policy policy, Company insuranceCompany) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        this.companyId = insuranceCompany.getId();
        this.company = insuranceCompany.getCompanyName();
        this.policyNumber = policy.getPolicyNumber();
        this.owner = policy.getPolicyOwner();
        this.pType = policy.getPolicyType();
        this.type = policy.getPolicyType().toString();
        this.date = LocalDate.ofInstant(policy.getPolicyDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        this.timestamp = policy.getPolicyDate();
        this.financer = policy.getFinancer();
        this.valid = policy.isValid();
        this.vin = policy.getVin();

        return this;
    }
    public List<PolicyView> build(List<Policy> policies, Company insuranceCompany) {
        List<PolicyView> views = new ArrayList<>();

        for(Policy policy: policies) {
            PolicyView view = new PolicyView().build(policy, insuranceCompany);
            views.add(view);
        }

        return views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PolicyView)) return false;
        PolicyView that = (PolicyView) o;
        return companyId.equals(that.companyId) &&
                company.equals(that.company) &&
                policyNumber.equals(that.policyNumber) &&
                date.equals(that.date) &&
                timestamp.equals(that.timestamp) &&
                financer.equals(that.financer) &&
                valid.equals(that.valid) &&
                vin.equals(that.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, company, policyNumber, date, timestamp, financer, valid, vin);
    }
}
