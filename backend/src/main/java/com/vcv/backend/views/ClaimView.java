package com.vcv.backend.views;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class ClaimView {
    @Autowired
    private CompanyRepository companyRepository;

    private String claimNumber;
    private String company;
    private String type;
    private String date;
    private String details;
    private String policyNumber;
    private String vin;

    public ClaimView() {}
    public ClaimView build(Claim claim) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        ClaimView view = new ClaimView();

        view.claimNumber = claim.getClaimNumber();
        view.company = companyRepository.findById(claim.getCompanyId()).get().getName();
        view.type = claim.getClaimType().toString();
        view.date = dateFormatter.format(claim.getClaimDate().toInstant());
        view.details = claim.getClaimDetails();
        view.policyNumber = claim.getPolicyNumber();
        view.vin = claim.getVin();

        return view;
    }
    public List<ClaimView> build(List<Claim> claims) {
        List<ClaimView> views = new ArrayList<>();

        for(Claim claim: claims) {
            ClaimView view = new ClaimView().build(claim);
            views.add(view);
        }

        return views;
    }
}
