package com.vcv.backend.views;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Policy;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PolicyView implements Serializable {
    private String company;
    private String policyNumber;
    private String date;
    private String financer;
    private Boolean valid;
    private String vin;

    public String getCompany() {
        return company;
    }
    public String getPolicyNumber() {
        return policyNumber;
    }
    public String getDate() {
        return date;
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

    public PolicyView() {}
    public PolicyView build(Policy policy, Company insuranceCompany) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        this.company = insuranceCompany.getCompanyName();
        this.policyNumber = policy.getPolicyNumber();
        this.date = LocalDate.ofInstant(policy.getPolicyDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
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
        return company.equals(that.company) &&
                policyNumber.equals(that.policyNumber) &&
                date.equals(that.date) &&
                financer.equals(that.financer) &&
                valid.equals(that.valid) &&
                vin.equals(that.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, policyNumber, date, financer, valid, vin);
    }
}
