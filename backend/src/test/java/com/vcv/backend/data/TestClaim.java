package com.vcv.backend.data;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.enums.ClaimType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestClaim {
    private Claim newClaim;
    private Claim updatedClaim;
    private List<Claim> claims;

    public TestClaim() {
        this.claims = new ArrayList<>();
        this.newClaim = new Claim.Builder()
                .setCompanyId(2L)
                .setClaimNumber("ISDFKG899RWEFAS")
                .setClaimType(ClaimType.Accident)
                .setClaimDate(Timestamp.valueOf(LocalDateTime.now()))
                .setClaimDetails("Some text here")
                .setPolicyNumber("D78FDG785563")
                .setValue(500)
                .setVin("4T1BE46K87U521931")
                .build();

        this.updatedClaim = new Claim.Builder()
                .setCompanyId(2L)
                .setClaimNumber("ISDFKG899RWEFAS")
                .setClaimType(ClaimType.Accident)
                .setClaimDate(Timestamp.valueOf(LocalDateTime.now()))
                .setClaimDetails("Right. The windshield goofed")
                .setPolicyNumber("D78FDG785563")
                .setValue(700)
                .setVin("4T1BE46K87U521931")
                .build();
    }

    public Claim getNewClaim() {
        return newClaim;
    }
    public Claim getUpdatedClaim() {
        return updatedClaim;
    }
    public List<Claim> getClaims() {
        return claims;
    }

    public void setClaims(List<Claim> claims) {
        this.claims = claims;
    }
}
