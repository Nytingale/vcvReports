package com.vcv.backend.views;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Company;
import com.vcv.backend.enums.ClaimType;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClaimView implements Serializable {
    private Long companyId;
    private ClaimType cType;
    private Timestamp timestamp;
    private String claimNumber;
    private String company;
    private String type;
    private String date;
    private Integer value;
    private String details;
    private String policyNumber;
    private Long jobId;
    private String vin;

    public ClaimType getCType() {
        return cType;
    }
    public Long getCompanyId() {
        return companyId;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public String getClaimNumber() {
        return claimNumber;
    }
    public String getCompany() {
        return company;
    }
    public String getType() {
        return type;
    }
    public String getDate() {
        return date;
    }
    public Integer getValue() {
        return value;
    }
    public String getDetails() {
        return details;
    }
    public String getPolicyNumber() {
        return policyNumber;
    }
    public Long getJobId() {
        return jobId;
    }
    public String getVin() {
        return vin;
    }

    public ClaimView setCType(ClaimType cType) {
        this.cType = cType;
        return this;
    }
    public ClaimView setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }
    public ClaimView setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }
    public ClaimView setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
        return this;
    }
    public ClaimView setCompany(String company) {
        this.company = company;
        return this;
    }
    public ClaimView setType(String type) {
        this.type = type;
        return this;
    }
    public ClaimView setDate(String date) {
        this.date = date;
        return this;
    }
    public ClaimView setValue(Integer value) {
        this.value = value;
        return this;
    }
    public ClaimView setDetails(String details) {
        this.details = details;
        return this;
    }
    public ClaimView setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
        return this;
    }
    public ClaimView setJobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }
    public ClaimView setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public ClaimView() {}
    public ClaimView build(Claim claim, Company insuranceCompany) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        this.cType = claim.getClaimType();
        this.companyId = claim.getCompanyId();
        this.timestamp = claim.getClaimDate();
        this.claimNumber = claim.getClaimNumber();
        this.company = insuranceCompany.getCompanyName();
        this.type = claim.getClaimType().toString();
        this.date = LocalDate.ofInstant(claim.getClaimDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        this.value = claim.getValue();
        this.details = claim.getClaimDetails();
        this.policyNumber = claim.getPolicyNumber();
        this.jobId = claim.getJobId();
        this.vin = claim.getVin();

        return this;
    }
    public List<ClaimView> build(List<Claim> claims, Company insuranceCompany) {
        List<ClaimView> views = new ArrayList<>();

        for(Claim claim: claims) {
            ClaimView view = new ClaimView().build(claim, insuranceCompany);
            views.add(view);
        }

        return views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClaimView)) return false;
        ClaimView claimView = (ClaimView) o;
        return timestamp.equals(claimView.timestamp) &&
                claimNumber.equals(claimView.claimNumber) &&
                company.equals(claimView.company) &&
                type.equals(claimView.type) &&
                date.equals(claimView.date) &&
                details.equals(claimView.details) &&
                policyNumber.equals(claimView.policyNumber) &&
                vin.equals(claimView.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, claimNumber, company, type, date, details, policyNumber, vin);
    }
}
