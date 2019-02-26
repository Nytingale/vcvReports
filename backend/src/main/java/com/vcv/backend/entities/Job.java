package com.vcv.backend.entities;

import com.vcv.backend.enums.JobType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Jobs")
public class Job {
    @Id
    private Long jobId;
    private Integer jobCost;
    private JobType jobType;
    private Timestamp jobDate;
    private String jobDetails;
    private String companyName;
    private String insuranceName;
    private String claimNumber;
    private String vin;

    public Long getJobId() {
        return jobId;
    }
    public Integer getJobCost() {
        return jobCost;
    }
    public JobType getJobType() {
        return jobType;
    }
    public Timestamp getJobDate() {
        return jobDate;
    }
    public String getJobDetails() {
        return jobDetails;
    }
    public String getCompanyName() {
        return companyName;
    }
    public String getInsuranceName() {
        return insuranceName;
    }
    public String getClaimNumber() {
        return claimNumber;
    }
    public String getVin() {
        return vin;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    public void setJobCost(Integer jobCost) {
        this.jobCost = jobCost;
    }
    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }
    public void setJobDate(Timestamp jobDate) {
        this.jobDate = jobDate;
    }
    public void setJobDetails(String jobDetails) {
        this.jobDetails = jobDetails;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }
    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, jobCost, jobType, jobDate, jobDetails, companyName, insuranceName, claimNumber, vin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;
        Job job = (Job) o;
        return jobId == job.jobId &&
                jobCost == job.jobCost &&
                jobType == job.jobType &&
                jobDate.equals(job.jobDate) &&
                jobDetails.equals(job.jobDetails) &&
                companyName.equals(job.companyName) &&
                Objects.equals(insuranceName, job.insuranceName) &&
                Objects.equals(claimNumber, job.claimNumber) &&
                vin.equals(job.vin);
    }
}
