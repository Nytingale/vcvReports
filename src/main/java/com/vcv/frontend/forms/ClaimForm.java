package com.vcv.frontend.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vcv.backend.views.ClaimView;
import com.vcv.frontend.views.InsuranceClaimView;
import com.vcv.frontend.views.InsuredVehicleView;

public class ClaimForm extends Div {
    private VerticalLayout content;

    private TextField claimNumber;
    private TextField type;
    private TextField date;
    private TextField policyNumber;
    private TextField vin;
    private TextField company;
    private Details details;

    private Button close;

    private String window;

    private ClaimView currentClaim;
    private Binder<ClaimView> binder;

    public ClaimForm(String view) {
        this.window = view;
        this.setClassName(window + "-form");

        content = new VerticalLayout();
        content.setSizeUndefined();

        claimNumber = new TextField("Claim Number");
        claimNumber.setWidth("100%");
        claimNumber.setRequired(true);
        claimNumber.setValueChangeMode(ValueChangeMode.EAGER);

        type = new TextField("Type");
        type.setWidth("100%");
        type.setRequired(true);
        type.setValueChangeMode(ValueChangeMode.EAGER);

        date = new TextField("Date");
        date.setWidth("100%");
        date.setRequired(true);
        date.setValueChangeMode(ValueChangeMode.EAGER);

        policyNumber = new TextField("Policy Number");
        policyNumber.setWidth("100%");
        policyNumber.setRequired(true);
        policyNumber.setValueChangeMode(ValueChangeMode.EAGER);

        vin = new TextField("VIN");
        vin.setWidth("100%");
        vin.setRequired(true);
        vin.setValueChangeMode(ValueChangeMode.EAGER);

        company = new TextField("Company");
        company.setWidth("100%");
        company.setRequired(true);
        company.setValueChangeMode(ValueChangeMode.EAGER);

        details = new Details();
        details.setSummaryText("Details...");
        details.addThemeVariants(DetailsVariant.FILLED);

        close = new Button("Close");
        close.setWidth("100%");
        close.addClickListener(event -> closeForm());

        binder = new BeanValidationBinder<>(ClaimView.class);
        binder.bindInstanceFields(this);

        content.add(claimNumber, type, date, policyNumber, vin, company, details, close);

        this.add(content);
    }

    private void closeForm() {
        switch (window) {
            case "insurance-claim":
                UI.getCurrent().navigate(InsuranceClaimView.class, "");
                break;

            case "insured-vehicle":
                UI.getCurrent().navigate(InsuredVehicleView.class, "");
                break;
        }
        displayForm(false);
    }

    public void displayForm(boolean flag) {
        this.setVisible(flag);
        this.setEnabled(flag);
    }

    public void displayClaim(ClaimView claim) {
        currentClaim = claim;
        binder.readBean(claim);
    }

    public void setDetailsContent(String text) {
        details.addContent(new TextField(text));
    }
}
