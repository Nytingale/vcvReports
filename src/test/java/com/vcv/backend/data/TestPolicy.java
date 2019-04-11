package com.vcv.backend.data;

import com.vcv.backend.entities.Policy;
import com.vcv.backend.enums.PolicyType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestPolicy {
    private Policy oldPolicy;
    private Policy newPolicy;
    private List<Policy> policies;

    public TestPolicy() {
        this.policies = new ArrayList<>();
        this.oldPolicy = null;
        this.newPolicy = new Policy.Builder()
                .setCompanyId(2L)
                .setPolicyNumber("D78FDG785563")
                .setPolicyOwner("Bobby Reynolds")
                .setPolicyDate(Timestamp.valueOf(LocalDateTime.now()))
                .setPolicyType(PolicyType.Third_Party)
                .setFinancer("")
                .setValid(true)
                .setVin("4T1BE46K87U521931")
                .build();
    }

    public Policy getOldPolicy() {
        return oldPolicy;
    }
    public Policy getNewPolicy() {
        return newPolicy;
    }
    public List<Policy> getPolicies() {
        return policies;
    }

    public void setOldPolicy(Policy oldPolicy) {
        this.oldPolicy = oldPolicy;
    }
    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
}
