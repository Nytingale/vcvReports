package com.vcv.backend.views;

import com.vcv.backend.entities.Policy;
import com.vcv.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class PolicyView {
    @Autowired
    private CompanyRepository companyRepository;

    private String company;
    private String policyNumber;
    private String date;
    private String financer;
    private Boolean valid;
    private String vin;

    public PolicyView() {}
    public PolicyView build(Policy policy) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        PolicyView view = new PolicyView();

        view.company = companyRepository.findById(policy.getCompanyId()).get().getCompanyName();
        view.policyNumber = policy.getPolicyNumber();
        view.date = dateFormatter.format(policy.getPolicyDate().toInstant());
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
