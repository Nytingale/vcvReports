package com.vcv.backend.views;

import com.vcv.backend.entities.Policy;
import com.vcv.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class PolicyView implements Serializable {
    @Autowired private CompanyRepository companyRepository;

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
    public PolicyView build(Policy policy) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        PolicyView view = new PolicyView();

        view.company = companyRepository.findById(policy.getCompanyId()).get().getCompanyName();
        view.policyNumber = policy.getPolicyNumber();
        view.date = LocalDate.ofInstant(policy.getPolicyDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        view.financer = policy.getFinancer();
        view.valid = policy.isValid();
        view.vin = policy.getVin();

        return view;
    }
    public List<PolicyView> build(List<Policy> policies) {
        List<PolicyView> views = new ArrayList<>();

        for(Policy policy: policies) {
            PolicyView view = new PolicyView().build(policy);
            views.add(view);
        }

        return views;
    }
}
