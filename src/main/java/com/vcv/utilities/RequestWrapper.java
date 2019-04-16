package com.vcv.utilities;

import com.vcv.backend.entities.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
        private HttpServletRequest request;
        private MultipartFile image;

        public MultipartFile getImage() {
            return image;
        }
        public HttpServletRequest getRequest() {
            return request;
        }

        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }
        public void setImage(MultipartFile image) {
            this.image = image;
        }
    }
    public static class Article extends Admin {
        private Article article;

        public Article getArticle() {
            return article;
        }
        public void setArticle(Article article) {
            this.article = article;
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

    public static class ClientCompany extends Admin {
        private User user;
        private Company company;

        public User getUser() {
            return user;
        }
        public Company getCompany() {
            return company;
        }

        public void setUser(User user) {
            this.user = user;
        }
        public void setCompany(Company clientCompany) {
            this.company = clientCompany;
        }
    }

    public static class Garage extends ClientCompany {
        private Job job;

        public Job getJob() {
            return job;
        }
        public void setJob(Job job) {
            this.job = job;
        }
    }
    public static class Insurance extends ClientCompany {
        private Claim claim;
        private Policy policy;

        public Claim getClaim() {
            return claim;
        }
        public Policy getPolicy() {
            return policy;
        }

        public void setClaim(Claim claim) {
            this.claim = claim;
        }
        public void setPolicy(Policy policy) {
            this.policy = policy;
        }
    }
    public static class Dealership extends ClientCompany {
        private Vehicle vehicle;

        public Vehicle getVehicle() {
            return vehicle;
        }
        public void setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
        }
    }
}
