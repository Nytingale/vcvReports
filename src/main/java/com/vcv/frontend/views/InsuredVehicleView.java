package com.vcv.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;

import com.vaadin.flow.router.*;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.views.ClaimView;
import com.vcv.backend.views.PolicyView;
import com.vcv.backend.views.UserView;
import com.vcv.backend.views.VehicleView;
import com.vcv.backend.services.VehicleService;

import com.vcv.frontend.MainLayout;
import com.vcv.frontend.forms.ClaimForm;
import com.vcv.frontend.forms.PolicyForm;
import com.vcv.frontend.forms.VehicleForm;
import com.vcv.frontend.grids.VehicleGrid;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "Insured Vehicles", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class InsuredVehicleView extends HorizontalLayout implements HasUrlParameter<String> {
    private UserView user;

    private Button newClaim;
    private Button newPolicy;

    private ClaimForm claimForm;
    private PolicyForm policyForm;
    private VehicleForm vehicleForm;
    private VehicleGrid vehicleGrid;

    private ListDataProvider<VehicleView> dataProvider;

    @Autowired private VehicleService vehicleService;

    public InsuredVehicleView() {
        this.setSizeFull();

        TextField filter = new TextField();
        filter.setPlaceholder("Search by VIN, Year, Make, or Model");
        filter.addValueChangeListener(event -> searchFilter(event.getValue()));

        vehicleGrid = new VehicleGrid();
        vehicleGrid.setDataProvider(dataProvider);
        vehicleGrid.asSingleSelect().addValueChangeListener(event -> openInsuredVehicleDetails(event.getValue()));

        claimForm = new ClaimForm("insured-vehicle");
        policyForm = new PolicyForm("insured-vehicle");
        vehicleForm = new VehicleForm("insured-vehicle");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(filter);
        verticalLayout.add(vehicleGrid);
        verticalLayout.setFlexGrow(1, vehicleGrid);
        verticalLayout.setFlexGrow(0, filter);
        verticalLayout.setSizeFull();
        verticalLayout.expand(vehicleGrid);

        newClaim = new Button("New Claim");
        newClaim.addClickListener(click -> newClaim());
        newClaim.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newClaim.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        newPolicy = new Button("New Policy");
        newPolicy.addClickListener(click -> newPolicy());
        newPolicy.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newPolicy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        this.add(verticalLayout);
        this.add(vehicleForm);
    }

    private void newClaim() {
        String vin = vehicleGrid.getSelectedRow().getVin();
        vehicleGrid.getSelectionModel().deselectAll();
        UI.getCurrent().navigate(InsuredVehicleView.class, "new claim");
        claimForm.displayForm(true);
        claimForm.displayClaim(new ClaimView().setVin(vin));
    }

    private void newPolicy() {
        String vin = vehicleGrid.getSelectedRow().getVin();
        vehicleGrid.getSelectionModel().deselectAll();
        UI.getCurrent().navigate(InsuredVehicleView.class, "new policy");
        policyForm.displayForm(true);
        policyForm.displayPolicy(new PolicyView().setVin(vin));
    }

    private void openInsuredVehicleDetails(VehicleView vehicle) {
        vehicleForm.displayForm(true);
        vehicleForm.displayVehicle(vehicle);
        vehicleForm.addButton(newClaim);
        vehicleForm.addButton(newPolicy);
    }

    private void searchFilter(String filter) {
        dataProvider.setFilter(vehicleView ->
                vehicleView.getVin().equals(filter) ||
                vehicleView.getInsurance().equals(filter) ||
                vehicleView.getPolicyNumber().equals(filter)
        );
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent,
                             @OptionalParameter String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            if (parameter.equals("new claim")) {
                newClaim();
            } else if(parameter.equals("new policy")) {
                newPolicy();
            } else {
                try {
                    VehicleView row = vehicleService.getVehicleView(parameter);
                    vehicleGrid.getSelectionModel().select(row);
                } catch (Exception e) {
                }
            }
        } else {
            vehicleForm.displayForm(false);
        }
    }
}
