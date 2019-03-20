package com.vcv.backend.views;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.User;
import com.vcv.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class UserView {
    @Autowired
    private CompanyRepository companyRepository;

    private String email;
    private String company;
    private String password;
    private String role;

    public UserView() {}
    public UserView build(User user) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        UserView view = new UserView();

        view.email = user.getEmail();
        view.company = companyRepository.findById(user.getCompanyId()).get().getName();;
        view.password = user.getPassword();

        if(user.getRoleId() == 1L) view.role = "User";
        else if(user.getRoleId() == 2L) view.role = "Admin";
        else if(user.getRoleId() == 3L) view.role = "Staff";
        else view.role = "Creator";

        return view;
    }
    public List<UserView> build(List<User> users) {
        List<UserView> views = new ArrayList<>();

        for(User user: users) {
            UserView view = new UserView().build(user);
            views.add(view);
        }

        return views;
    }
}
