package com.vcv.backend.views;

import com.vcv.backend.entities.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class UserView {
    private String email;
    private String companyName;
    private String password;
    private String companyType;
    private String subscriptionStartDate;
    private String subscriptionEndDate;
    private Boolean blacklisted;
    private Boolean warning;
    private Boolean admin;
    private Boolean valid;

    public UserView() {}
    public UserView build(User user) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        UserView view = new UserView();

        view.email = user.getEmail();
        view.companyName = user.getCompanyName();
        view.password = user.getPassword();
        view.companyType = user.getCompanyType().toString();
        view.subscriptionStartDate = dateFormatter.format(user.getSubscriptionStartDate().toInstant());
        view.subscriptionEndDate = dateFormatter.format(user.getSubscriptionEndDate().toInstant());
        view.blacklisted = user.isBlackisted();
        view.warning = LocalDate.ofInstant(user.getSubscriptionEndDate().toInstant(), ZoneId.systemDefault()).getDayOfYear() - LocalDate.now().getDayOfYear() <= 7;
        view.admin = user.isAdmin();
        view.valid = user.isValid();

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

    public static class CompanyView {
        private String companyName;
        private String companyType;
        private Boolean blacklisted;
        private Boolean valid;

        public CompanyView() {}
        public CompanyView build(UserView user) {
            CompanyView view = new CompanyView();

            view.companyName = user.companyName;
            view.companyType = user.companyType;
            view.blacklisted = user.blacklisted;
            view.valid = user.valid;

            return view;
        }
        public List<CompanyView> build(List<UserView> users) {
            List<CompanyView> views = new ArrayList<>();

            for(UserView user: users) {
                CompanyView view = new CompanyView().build(user);
                views.add(view);
            }

            return views;
        }
    }
    public static class SubscriptionConsole {
        private String subscriptionStartDate;
        private String subscriptionEndDate;
        private String type;
        private String name;
        private Boolean warning;

        public SubscriptionConsole() {}
        public SubscriptionConsole build(UserView user) {
            SubscriptionConsole view = new SubscriptionConsole();

            view.subscriptionStartDate = user.subscriptionStartDate;
            view.subscriptionEndDate = user.subscriptionEndDate;
            view.type = user.companyType;
            view.name = user.companyName;
            view.warning = user.warning;

            return view;
        }
    }
}
