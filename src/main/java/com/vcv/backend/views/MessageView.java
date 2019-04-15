package com.vcv.backend.views;

import com.vcv.backend.entities.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class MessageView implements Serializable {
    protected String message;

    public String getMessage() {
        return message;
    }

    public MessageView() {}
    public MessageView build(String message) {
        this.message = message;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageView)) return false;
        MessageView that = (MessageView) o;
        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    public static class FileUpload extends MessageView {
        private String company;
        private String name;
        private String type;
        private Long size;
        
        public String getCompany() {
            return company;
        }
        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
        public Long getSize() {
            return size;
        }

        public FileUpload() {}
        public FileUpload build(MultipartFile file, String company, String message) {
            this.message = message;
            this.company = company;
            this.name = file.getName();
            this.type = file.getContentType();
            this.size = file.getSize();

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FileUpload)) return false;
            FileUpload that = (FileUpload) o;
            return message.equals(that.message) &&
                    company.equals(that.company) &&
                    name.equals(that.name) &&
                    type.equals(that.type) &&
                    size.equals(that.size);
        }

        @Override
        public int hashCode() {
            return Objects.hash(message, company, name, type, size);
        }
    }

    public static class JobReport extends MessageView {

        private Long id;

        public Long getId() {
            return id;
        }

        public JobReport() {}
        public JobReport build(Job job, String message) {
            this.id = job.getId();
            this.message = message;

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof JobReport)) return false;
            JobReport jobReport = (JobReport) o;
            return id.equals(jobReport.id) &&
                    message.equals(jobReport.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, message);
        }
    }
    public static class UserReport extends MessageView {
        private String email;
        private String company;

        public String getEmail() {
            return email;
        }
        public String getCompany() {
            return company;
        }

        public UserReport() {}
        public UserReport build(User user, Company userCompany, String message) {
            this.email = user.getEmail();
            this.company = userCompany.getCompanyName();
            this.message = message;

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserReport)) return false;
            UserReport that = (UserReport) o;
            return email.equals(that.email) &&
                    company.equals(that.company) &&
                    message.equals(that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(email, company, message);
        }
    }
    public static class StolenReport extends MessageView {
        private String vin;
        private Boolean stolen;

        public String getVin() {
            return vin;
        }
        public Boolean getStolen() {
            return stolen;
        }

        public StolenReport() {}
        public StolenReport build(Vehicle vehicle, String message) {
            this.vin = vehicle.getVin();
            this.stolen = vehicle.isStolen();
            this.message = message;

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StolenReport)) return false;
            StolenReport that = (StolenReport) o;
            return vin.equals(that.vin) &&
                    stolen.equals(that.stolen) &&
                    message.equals(that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vin, stolen, message);
        }
    }
    public static class Registration extends MessageView {
        private String vin;

        public String getVin() {
            return vin;
        }

        public Registration() {}
        public Registration build(Vehicle vehicle, String message) {
            this.vin = vehicle.getVin();
            this.message = message;

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Registration)) return false;
            Registration that = (Registration) o;
            return vin.equals(that.vin) &&
                    message.equals(that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vin, message);
        }
    }
    public static class SalvageReport extends MessageView {
        private String vin;
        private Integer amount;

        public String getVin() {
            return vin;
        }
        public Integer getAmount() {
            return amount;
        }

        public SalvageReport() {}
        public SalvageReport build(Vehicle vehicle, String message) {
            this.vin = vehicle.getVin();
            this.amount = vehicle.getNumSalvages();
            this.message = message;

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SalvageReport)) return false;
            SalvageReport that = (SalvageReport) o;
            return vin.equals(that.vin) &&
                    amount.equals(that.amount) &&
                    message.equals(that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vin, amount, message);
        }
    }
    public static class CompanyReport extends MessageView {
        private String name;
        private String type;
        private String website;
        private String subscriptionStartDate;
        private String subscriptionEndDate;

        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
        public String getWebsite() {
            return website;
        }
        public String getSubscriptionStartDate() {
            return subscriptionStartDate;
        }
        public String getSubscriptionEndDate() {
            return subscriptionEndDate;
        }

        public CompanyReport() {}
        public CompanyReport build(Company company, String message) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

            this.name = company.getCompanyName();
            this.type = company.getCompanyType().toString();
            this.website = company.getWebsite();
            this.subscriptionStartDate = LocalDate.ofInstant(company.getSubscriptionStartDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
            this.subscriptionEndDate = LocalDate.ofInstant(company.getSubscriptionEndDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
            this.message = message;

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompanyReport)) return false;
            CompanyReport that = (CompanyReport) o;
            return name.equals(that.name) &&
                    type.equals(that.type) &&
                    website.equals(that.website) &&
                    subscriptionStartDate.equals(that.subscriptionStartDate) &&
                    subscriptionEndDate.equals(that.subscriptionEndDate) &&
                    message.equals(that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, type, website, subscriptionStartDate, subscriptionEndDate, message);
        }
    }
    public static class AccidentReport extends MessageView {
        private String vin;
        private Boolean stolen;

        public String getVin() {
            return vin;
        }
        public Boolean getStolen() {
            return stolen;
        }

        public AccidentReport() {}
        public AccidentReport build(Vehicle vehicle, String message) {
            this.vin = vehicle.getVin();
            this.stolen = vehicle.isStolen();
            this.message = message;

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AccidentReport)) return false;
            AccidentReport that = (AccidentReport) o;
            return vin.equals(that.vin) &&
                    stolen.equals(that.stolen) &&
                    message.equals(that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vin, stolen, message);
        }
    }
    public static class InsuranceReport extends MessageView {
        private String number;
        private String company;

        public String getNumber() {
            return number;
        }
        public String getCompany() {
            return company;
        }

        public InsuranceReport() {}
        public InsuranceReport build(Claim claim, Company insuranceCompany, String message) {
            this.number = claim.getClaimNumber();
            this.company = insuranceCompany.getCompanyName();
            this.message = message;

            return this;
        }
        public InsuranceReport build(Policy policy, Company insuranceCompany, String message) {
            this.number = policy.getPolicyNumber();
            this.company = insuranceCompany.getCompanyName();
            this.message = message;

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof InsuranceReport)) return false;
            InsuranceReport that = (InsuranceReport) o;
            return number.equals(that.number) &&
                    company.equals(that.company) &&
                    message.equals(that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(number, company, message);
        }
    }
    public static class WriteOffReport extends MessageView {
        private String vin;
        private Boolean writtenOff;

        public String getVin() {
            return vin;
        }
        public Boolean getWrittenOff() {
            return writtenOff;
        }

        public WriteOffReport() {}
        public WriteOffReport build(Vehicle vehicle, String message) {
            this.vin = vehicle.getVin();
            this.writtenOff = vehicle.isWrittenOff();
            this.message = message;

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WriteOffReport)) return false;
            WriteOffReport that = (WriteOffReport) o;
            return vin.equals(that.vin) &&
                    writtenOff.equals(that.writtenOff) &&
                    message.equals(that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vin, writtenOff, message);
        }
    }

    public static class ArticleReport extends MessageView {
        private String title;

        public String getTitle() {
            return title;
        }

        public ArticleReport() {}
        public ArticleReport build(Article article, String message) {
            this.title = article.getTitle();
            this.message = message;

            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ArticleReport)) return false;
            if (!super.equals(o)) return false;
            ArticleReport that = (ArticleReport) o;
            return title.equals(that.title);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), title);
        }
    }
}
