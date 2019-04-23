package com.vcv.frontend.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import com.vaadin.flow.data.value.ValueChangeMode;
import com.vcv.backend.views.PolicyView;
import com.vcv.frontend.views.InsuredVehicleView;


public class PolicyForm extends Div {
    private VerticalLayout content;

    private TextField policyNumber;
    private TextField date;
    private TextField financer;
    private TextField vin;
    private Checkbox valid;
    private TextField company;

    private Button close;

    private String window;

    private PolicyView currentPolicy;
    private Binder<PolicyView> binder;

    public PolicyForm(String view) {
        this.window = view;
        this.setClassName(window + "-form");

        content = new VerticalLayout();
        content.setSizeUndefined();

        policyNumber = new TextField("Policy Number");
        policyNumber.setWidth("100%");
        policyNumber.setRequired(true);
        policyNumber.setValueChangeMode(ValueChangeMode.EAGER);

        date = new TextField("Date");
        date.setWidth("100%");
        date.setRequired(true);
        date.setValueChangeMode(ValueChangeMode.EAGER);

        financer = new TextField("Financed By");
        financer.setWidth("100%");
        financer.setValueChangeMode(ValueChangeMode.EAGER);

        vin = new TextField("VIN");
        vin.setWidth("100%");
        vin.setRequired(true);
        vin.setValueChangeMode(ValueChangeMode.EAGER);

        valid = new Checkbox("Valid");

        company = new TextField("Company");
        company.setWidth("100%");
        company.setRequired(true);
        company.setValueChangeMode(ValueChangeMode.EAGER);

        close = new Button("Close");
        close.setWidth("100%");
        close.addClickListener(event -> closeForm());

        binder = new BeanValidationBinder<>(PolicyView.class);
        binder.bindInstanceFields(this);

        content.add(policyNumber, date, financer, vin, valid, company, close);

        this.add(content);
    }

    private void closeForm() {
        switch (window) {
            case "insured-vehicle":
                UI.getCurrent().navigate(InsuredVehicleView.class, "");
                break;

            case "insurance-policy":
                UI.getCurrent().navigate(InsurancePolicyView.class, "");
                break;
        }
        displayForm(false);
    }

    public void displayForm(boolean flag) {
        this.setVisible(flag);
        this.setEnabled(flag);
    }

    public void displayPolicy(PolicyView policy) {
        currentPolicy = policy;
        binder.readBean(policy);
    }
}
