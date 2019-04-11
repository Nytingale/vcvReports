package com.vcv.backend.views;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Company;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClaimView implements Serializable {
    private String claimNumber;
    private String company;
    private String type;
    private String date;
    private String details;
    private String policyNumber;
    private String vin;

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
    public String getDetails() {
        return details;
    }
    public String getPolicyNumber() {
        return policyNumber;
    }
    public String getVin() {
        return vin;
    }

    public ClaimView() {}
    public ClaimView build(Claim claim, Company insuranceCompany) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        ClaimView view = new ClaimView();

        view.claimNumber = claim.getClaimNumber();
        view.company = insuranceCompany.getCompanyName();
        view.type = claim.getClaimType().toString();
        view.date = LocalDate.ofInstant(claim.getClaimDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        view.details = claim.getClaimDetails();
        view.policyNumber = claim.getPolicyNumber();
        view.vin = claim.getVin();

        return view;
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
        return claimNumber.equals(claimView.claimNumber) &&
                company.equals(claimView.company) &&
                type.equals(claimView.type) &&
                date.equals(claimView.date) &&
                details.equals(claimView.details) &&
                policyNumber.equals(claimView.policyNumber) &&
                vin.equals(claimView.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(claimNumber, company, type, date, details, policyNumber, vin);
    }
}
