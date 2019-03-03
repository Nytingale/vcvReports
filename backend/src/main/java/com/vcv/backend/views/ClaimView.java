package com.vcv.backend.views;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.enums.ClaimType;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class ClaimView {
    private String claimNumber;
    private String companyName;
    private ClaimType claimType;
    private String claimDate;
    private String claimDetails;
    private String policyNumber;
    private String vin;

    public ClaimView() {}
    public ClaimView build(Claim claim) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        ClaimView view = new ClaimView();

        view.claimNumber = claim.getClaimNumber();
        view.companyName = claim.getCompanyName();
        view.claimType = claim.getClaimType();
        view.claimDate = dateFormatter.format(claim.getClaimDate().toInstant());
        view.claimDetails = claim.getClaimDetails();
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
