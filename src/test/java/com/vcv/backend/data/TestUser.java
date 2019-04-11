package com.vcv.backend.data;

import com.vcv.backend.entities.User;

import java.util.ArrayList;
import java.util.List;

public class TestUser {
    private User vcv;
    private User admin;
    private User staff;
    private User client;
    private User newAdmin;
    private User newEmployee;
    private List<User> users;
    private String vcvString;
    private String adminString;
    private String staffString;
    private String newPassword;
    private String oldPassword;
    private String clientString;

    public TestUser() {
        this.vcv = null;
        this.admin = null;
        this.staff = null;
        this.newAdmin = new User.Builder()
                .setEmail("TestEmail1@email.com")
                .setPassword("newEmailTest123")
                .setPasswordReset(false)
                .setCompanyId(1L)
                .setRoleId(2L)
                .build();
        this.newEmployee = new User.Builder()
                .setEmail("TestEmail@email.com")
                .setPassword("newEmailTest123")
                .setPasswordReset(false)
                .setCompanyId(2L)
                .setRoleId(2L)
                .build();
        this.users = new ArrayList<>();
        this.vcvString = "";
        this.adminString = "";
        this.staffString = "";
        this.newPassword = "";
        this.oldPassword = "";
        this.clientString = "";
    }

    public User getVcv() {
        return vcv;
    }
    public User getAdmin() {
        return admin;
    }
    public User getStaff() {
        return staff;
    }
    public User getClient() {
        return client;
    }
    public User getNewAdmin() {
        return newAdmin;
    }
    public User getNewEmployee() {
        return newEmployee;
    }
    public List<User> getUsers() {
        return users;
    }
    public String getVcvString() {
        return vcvString;
    }
    public String getAdminString() {
        return adminString;
    }
    public String getStaffString() {
        return staffString;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public String getOldPassword() {
        return oldPassword;
    }
    public String getClientString() {
        return clientString;
    }

    public void setVcv(User vcv) {
        this.vcv = vcv;
    }
    public void setAdmin(User admin) {
        this.admin = admin;
    }
    public void setStaff(User staff) {
        this.staff = staff;
    }
    public void setClient(User client) {
        this.client = client;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    public void setVcvString(String vcvString) {
        this.vcvString = vcvString;
    }
    public void setAdminString(String adminString) {
        this.adminString = adminString;
    }
    public void setStaffString(String staffString) {
        this.staffString = staffString;
    }
    public void setNewPassword(String newPassword) {
        this.oldPassword = "abcxyz123";
        this.newPassword = newPassword;
    }
    public void setClientString(String clientString) {
        this.clientString = clientString;
    }
}
