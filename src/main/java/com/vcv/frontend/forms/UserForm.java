package com.vcv.frontend.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vcv.backend.views.UserView;
import com.vcv.frontend.views.AdminView;

public class UserForm extends Div {
    private VerticalLayout content;

    private TextField email;
    private TextField role;
    private TextField company;

    private Button close;

    private Button makeAdmin;
    private Button resetPassword;
    private Button removeEmployee;

    private String window;

    private UserView currentUser;
    private Binder<UserView> binder;

    public UserForm(String view) {
        this.window = view;
        this.setClassName(window + "-form");

        content = new VerticalLayout();
        content.setSizeUndefined();

        email = new TextField("Email");
        email.setWidth("100%");
        email.setRequired(true);
        email.setValueChangeMode(ValueChangeMode.EAGER);

        role = new TextField("Role");
        role.setWidth("100%");
        role.setRequired(true);
        role.setValueChangeMode(ValueChangeMode.EAGER);

        company = new TextField("Company");
        company.setWidth("100%");
        company.setRequired(true);
        company.setValueChangeMode(ValueChangeMode.EAGER);

        makeAdmin = new Button("Make Admin");
        makeAdmin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        makeAdmin.setEnabled(false);
        makeAdmin.setVisible(false);
        makeAdmin.setWidth("100%");

        resetPassword = new Button("Reset Password");
        resetPassword.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetPassword.setEnabled(false);
        resetPassword.setVisible(false);
        resetPassword.setWidth("100%");

        removeEmployee = new Button("Remove Employee");
        removeEmployee.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removeEmployee.setEnabled(false);
        removeEmployee.setVisible(false);
        removeEmployee.setWidth("100%");

        close = new Button("Close");
        close.setWidth("100%");
        close.addClickListener(event -> closeForm());

        binder = new BeanValidationBinder<>(UserView.class);
        binder.bindInstanceFields(this);

        content.add(email, role, company, makeAdmin, resetPassword, removeEmployee, close);

        this.add(content);
    }

    public UserView currentUser() {
        return currentUser;
    }

    public Binder<UserView> binder() {
        return this.binder;
    }

    public Button makeAdminBtn() {
        return makeAdmin;
    }

    public Button resetPasswordBtn() {
        return resetPassword;
    }

    public Button removeEmployeeBtn() {
        return removeEmployee;
    }

    public void displayUser(UserView user) {
        currentUser = user;
        binder.readBean(user);
    }

    public void displayForm(boolean flag) {
        this.setEnabled(flag);
        this.setVisible(flag);
        makeAdmin.setVisible(flag);
        makeAdmin.setEnabled(flag);
        removeEmployee.setVisible(flag);
        removeEmployee.setEnabled(flag);
    }

    private void closeForm() {
        switch(window) {
            case "company-employees":
                UI.getCurrent().navigate(AdminView.class, "");
                break;
        }
        displayForm(false);
    }
}
