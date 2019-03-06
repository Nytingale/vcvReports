package com.vcv.backend.views;

import com.vcv.backend.entities.Policy;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class PolicyView {
    private String companyName;
    private String policyNumber;
    private String policyDate;
    private String financer;
    private Boolean valid;
    private String vin;

    public PolicyView() {}
    public PolicyView build(Policy policy) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        PolicyView view = new PolicyView();

        view.companyName = policy.getCompanyName();
        view.policyNumber = policy.getPolicyNumber();
        view.policyDate = dateFormatter.format(policy.getPolicyDate().toInstant());
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
