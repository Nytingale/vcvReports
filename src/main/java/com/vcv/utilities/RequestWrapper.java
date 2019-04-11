package com.vcv.utilities;

import com.vcv.backend.entities.User;
import com.vcv.backend.entities.Company;

import org.springframework.web.multipart.MultipartFile;

public class RequestWrapper {
    private String details;

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    public static class Admin extends RequestWrapper {
        private User admin;

        public User getAdmin() {
            return admin;
        }
        public void setAdmin(User admin) {
            this.admin = admin;
        }
    }

    public static class Image extends Admin {
        private MultipartFile image;

        public MultipartFile getImage() {
            return image;
        }
        public void setImage(MultipartFile image) {
            this.image = image;
        }
    }
    public static class Employee extends Admin {
        private User employee;

        public User getEmployee() {
            return employee;
        }
        public void setEmployee(User employee) {
            this.employee = employee;
        }
    }
    public static class Registration extends Admin {
        private User vcv;
        private Company company;

        public User getVcv() {
            return vcv;
        }
        public Company getCompany() {
            return company;
        }

        public void setVcv(User vcv) {
            this.vcv = vcv;
        }
        public void setCompany(Company company) {
            this.company = company;
        }
    }
}
