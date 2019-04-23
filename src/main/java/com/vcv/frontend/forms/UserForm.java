package com.vcv.frontend.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vcv.backend.views.UserView;

public class UserForm extends Div {
    private VerticalLayout content;

    private TextField email;
    private TextField role;
    private TextField company;

    private Button close;

    private UserView currentUser;
    private Binder<UserView> binder;

    public UserForm() {
        this.setClassName("user-form");

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

        close = new Button("Close");
        close.setWidth("100%");
        close.addClickListener(event -> closeForm());

        binder = new BeanValidationBinder<>(UserView.class);
        binder.bindInstanceFields(this);

        content.add(email, role, company, close);

        this.add(content);
    }

    private void closeForm() {
        UI.getCurrent().navigate(EmployeeView.class, "");
        displayForm(false);
    }

    public void displayForm(boolean flag) {
        this.setVisible(flag);
        this.setEnabled(flag);
    }

    public void displayUser(UserView user) {
        currentUser = user;
        binder.readBean(user);
    }
}
