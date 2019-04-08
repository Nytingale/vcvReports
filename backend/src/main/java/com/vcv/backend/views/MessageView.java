package com.vcv.backend.views;

import com.vcv.backend.entities.*;
import com.vcv.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class MessageView implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public MessageView() {}
    public MessageView build(String message) {
        MessageView view = new MessageView();

        view.message = message;

        return view;
    }

    public static class FileUpload implements Serializable {
        private MessageView message;
        private String company;
        private String name;
        private String type;
        private Long size;

        public MessageView getMessage() {
            return message;
        }
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
            FileUpload view = new FileUpload();

            view.message = new MessageView().build(message);
            view.company = company;
            view.name = file.getName();
            view.type = file.getContentType();
            view.size = file.getSize();

            return view;
        }
    }

    public static class JobReport implements Serializable {
        private Long id;
        private MessageView message;

        public Long getId() {
            return id;
        }
        public MessageView getMessage() {
            return message;
        }

        public JobReport() {}
        public JobReport build(Job job, String message) {
            JobReport view = new JobReport();

            view.id = job.getId();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class UserReport implements Serializable {
        @Autowired private CompanyRepository companyRepository;

        private String email;
        private String company;
        private MessageView message;

        public String getEmail() {
            return email;
        }
        public String getCompany() {
            return company;
        }
        public MessageView getMessage() {
            return message;
        }

        public UserReport() {}
        public UserReport build(User user, String message) {
            UserReport view = new UserReport();

            view.email = user.getEmail();
            view.company = companyRepository.findById(user.getCompanyId()).get().getCompanyName();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class StolenReport implements Serializable {
        private String vin;
        private Boolean stolen;
        private MessageView message;

        public String getVin() {
            return vin;
        }
        public Boolean getStolen() {
            return stolen;
        }
        public MessageView getMessage() {
            return message;
        }

        public StolenReport() {}
        public StolenReport build(Vehicle vehicle, String message) {
            StolenReport view = new StolenReport();

            view.vin = vehicle.getVin();
            view.stolen = vehicle.isStolen();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class Registration implements Serializable {
        private String vin;
        private MessageView message;

        public String getVin() {
            return vin;
        }
        public MessageView getMessage() {
            return message;
        }

        public Registration() {}
        public Registration build(Vehicle vehicle, String message) {
            Registration view = new Registration();

            view.vin = vehicle.getVin();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class SalvageReport implements Serializable {
        private String vin;
        private Integer amount;
        private MessageView message;

        public String getVin() {
            return vin;
        }
        public Integer getAmount() {
            return amount;
        }
        public MessageView getMessage() {
            return message;
        }

        public SalvageReport() {}
        public SalvageReport build(Vehicle vehicle, String message) {
            SalvageReport view = new SalvageReport();

            view.vin = vehicle.getVin();
            view.amount = vehicle.getNumSalvages();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class CompanyReport implements Serializable {
        private String name;
        private String type;
        private String website;
        private String subscriptionStartDate;
        private String subscriptionEndDate;
        private MessageView message;

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
        public MessageView getMessage() {
            return message;
        }

        public CompanyReport() {}
        public CompanyReport build(Company company, String message) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
            CompanyReport view = new CompanyReport();

            view.name = company.getCompanyName();
            view.type = company.getCompanyType().toString();
            view.website = company.getWebsite();
            view.subscriptionStartDate = LocalDate.ofInstant(company.getSubscriptionStartDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
            view.subscriptionEndDate = LocalDate.ofInstant(company.getSubscriptionEndDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class AccidentReport implements Serializable {
        private String vin;
        private Boolean stolen;
        private MessageView message;

        public String getVin() {
            return vin;
        }
        public Boolean getStolen() {
            return stolen;
        }
        public MessageView getMessage() {
            return message;
        }

        public AccidentReport() {}
        public AccidentReport build(Vehicle vehicle, String message) {
            AccidentReport view = new AccidentReport();

            view.vin = vehicle.getVin();
            view.stolen = vehicle.isStolen();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class InsuranceReport implements Serializable {
        @Autowired private CompanyRepository companyRepository;

        private String number;
        private String company;
        private MessageView message;

        public String getNumber() {
            return number;
        }
        public String getCompany() {
            return company;
        }
        public MessageView getMessage() {
            return message;
        }

        public InsuranceReport() {}
        public InsuranceReport build(Claim claim, String message) {
            InsuranceReport view = new InsuranceReport();

            view.number = claim.getClaimNumber();
            view.company = companyRepository.findById(claim.getCompanyId()).get().getCompanyName();
            view.message = new MessageView().build(message);

            return view;
        }
        public InsuranceReport build(Policy policy, String message) {
            InsuranceReport view = new InsuranceReport();

            view.number = policy.getPolicyNumber();
            view.company = companyRepository.findById(policy.getCompanyId()).get().getCompanyName();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class WriteOffReport implements Serializable {
        private String vin;
        private Boolean writtenOff;
        private MessageView message;

        public String getVin() {
            return vin;
        }
        public Boolean getWrittenOff() {
            return writtenOff;
        }
        public MessageView getMessage() {
            return message;
        }

        public WriteOffReport() {}
        public WriteOffReport build(Vehicle vehicle, String message) {
            WriteOffReport view = new WriteOffReport();

            view.vin = vehicle.getVin();
            view.writtenOff = vehicle.isWrittenOff();
            view.message = new MessageView().build(message);

            return view;
        }
    }
}
