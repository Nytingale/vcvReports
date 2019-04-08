package com.vcv.backend.entities;

import com.vcv.backend.enums.JobType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "job")
public class Job {
    @Id
    private Long id;
    private Integer jobCost;

    @Enumerated(EnumType.STRING)
    private JobType jobType;
    private Timestamp jobDate;
    private String jobDetails;
    private String claimNumber;
    private String vin;
    private Long insuranceId;
    private Long companyId;

    public Long getId() {
        return id;
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
    public String getClaimNumber() {
        return claimNumber;
    }
    public String getVin() {
        return vin;
    }
    public Long getInsuranceId() {
        return insuranceId;
    }
    public Long getCompanyId() {
        return companyId;
    }

    public void setId(Long id) {
        this.id = id;
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
    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }
    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobCost, jobType, jobDate, jobDetails, companyId, insuranceId, claimNumber, vin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;
        Job job = (Job) o;
        return id == job.id &&
                jobCost == job.jobCost &&
                jobType == job.jobType &&
                jobDate.equals(job.jobDate) &&
                jobDetails.equals(job.jobDetails) &&
                companyId.equals(job.companyId) &&
                Objects.equals(insuranceId, job.insuranceId) &&
                Objects.equals(claimNumber, job.claimNumber) &&
                vin.equals(job.vin);
    }

    public static class Builder {
        private Long id;
        private Integer jobCost;
        private JobType jobType;
        private Timestamp jobDate;
        private String jobDetails;
        private String claimNumber;
        private String vin;
        private Long insuranceId;
        private Long companyId;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }
        public Builder setJobCost(Integer jobCost) {
            this.jobCost = jobCost;
            return this;
        }
        public Builder setJobType(JobType jobType) {
            this.jobType = jobType;
            return this;
        }
        public Builder setJobDate(Timestamp jobDate) {
            this.jobDate = jobDate;
            return this;
        }
        public Builder setJobDetails(String jobDetails) {
            this.jobDetails = jobDetails;
            return this;
        }
        public Builder setClaimNumber(String claimNumber) {
            this.claimNumber = claimNumber;
            return this;
        }
        public Builder setVin(String vin) {
            this.vin = vin;
            return this;
        }
        public Builder setInsuranceId(Long insuranceId) {
            this.insuranceId = insuranceId;
            return this;
        }
        public Builder setCompanyId(Long companyId) {
            this.companyId = companyId;
            return this;
        }

        public Job build() {
            Job job = new Job();

            job.setId(this.id);
            job.setJobCost(this.jobCost);
            job.setJobType(this.jobType);
            job.setJobDate(this.jobDate);
            job.setJobDetails(this.jobDetails);
            job.setClaimNumber(this.claimNumber);
            job.setVin(this.vin);
            job.setInsuranceId(this.insuranceId);
            job.setCompanyId(this.companyId);

            return job;
        }
    }
}

