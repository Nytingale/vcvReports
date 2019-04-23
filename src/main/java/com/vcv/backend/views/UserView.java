package com.vcv.backend.views;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.UserServiceException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserView implements Serializable {
    private String email;
    private String company;
    private String password;
    private String role;

    public String getEmail() {
        return email;
    }
    public String getCompany() {
        return company;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }

    public UserView setEmail(String email) {
        this.email = email;
        return this;
    }
    public UserView setCompany(String company) {
        this.company = company;
        return this;
    }
    public UserView setPassword(String password) {
        this.password = password;
        return this;
    }
    public UserView setRole(String role) {
        this.role = role;
        return this;
    }

    public UserView() {}
    public UserView build(User user, Company userCompany) {
        this.email = user.getEmail();
        this.company = userCompany.getCompanyName();
        this.password = user.getPassword();

        if(user.getRoleId() == 1L) this.role = "Admin";
        else if(user.getRoleId() == 2L) this.role = "Admin";
        else if(user.getRoleId() == 3L) this.role = "Staff";
        else this.role = "Creator";

        return this;
    }
    public List<UserView> build(List<User> users, Company company) {
        List<UserView> views = new ArrayList<>();

        for(User user: users) {
            UserView view = new UserView().build(user, company);
            views.add(view);
        }

        return views;
    }
    public List<UserView> build(List<User> users, List<Company> companies) throws UserServiceException {
        List<UserView> views = new ArrayList<>();

        if(users.size() != companies.size()) {
            throw new UserServiceException("build(List<Admin>, List<ClientCompany>) does not have equal Lists");
        }

        for(int x=0; x<users.size(); x++) {
            User user = users.get(x);
            Company company = companies.get(x);
            UserView view = new UserView().build(user, company);
            views.add(view);
        }

        return views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserView)) return false;
        UserView userView = (UserView) o;
        return email.equals(userView.email) &&
                company.equals(userView.company) &&
                password.equals(userView.password) &&
                role.equals(userView.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, company, password, role);
    }
}
