package com.vcv.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.*;
import com.vcv.backend.services.ClaimService;
import com.vcv.backend.views.ClaimView;
import com.vcv.backend.views.UserView;
import com.vcv.frontend.MainLayout;
import com.vcv.frontend.forms.ClaimForm;
import com.vcv.frontend.grids.ClaimGrid;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "Claims", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class InsuranceClaimView extends HorizontalLayout implements HasUrlParameter<String> {
    private UserView user;

    private Button newClaim;

    private ClaimForm claimForm;
    private ClaimGrid claimGrid;

    private ListDataProvider<ClaimView> dataProvider;

    @Autowired private ClaimService claimService;

    private InsuranceClaimView() {
        this.setSizeFull();

        TextField filter = new TextField();
        filter.setPlaceholder("Search by Claim Number, Policy Number, or VIN");
        filter.addValueChangeListener(event -> searchFilter(event.getValue()));

        try {
            List<ClaimView> claims = claimService.getInsuranceClaims(user.getCompany());
            dataProvider = DataProvider.ofCollection(claims);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        claimGrid = new ClaimGrid();
        claimGrid.setDataProvider(dataProvider);
        claimGrid.asSingleSelect().addValueChangeListener(event -> openInsuranceClaimDetails(event.getValue()));

        claimForm = new ClaimForm("insurance-claim");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(filter);
        verticalLayout.add(claimGrid);
        verticalLayout.setFlexGrow(1, claimGrid);
        verticalLayout.setFlexGrow(0, filter);
        verticalLayout.setSizeFull();
        verticalLayout.expand(claimGrid);

        newClaim = new Button("New Claim");
        newClaim.addClickListener(click -> newClaim());
        newClaim.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newClaim.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        this.add(verticalLayout);
        this.add(claimForm);
    }

    private void newClaim() {
        claimGrid.getSelectionModel().deselectAll();
        UI.getCurrent().navigate(InsuranceClaimView.class, "new claim");
        claimForm.displayForm(true);
        claimForm.displayClaim(new ClaimView());
    }

    private void openInsuranceClaimDetails(ClaimView claim) {
        claimForm.displayForm(true);
        claimForm.displayClaim(claim);
    }

    private void searchFilter(String filter) {
        dataProvider.setFilter(claimView ->
                claimView.getVin().equals(filter) ||
                claimView.getClaimNumber().equals(filter) ||
                claimView.getPolicyNumber().equals(filter)
        );
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent,
                             @OptionalParameter String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            if (parameter.equals("new claim")) {
                newClaim();
            } else {
                try {
                    ClaimView row = claimService.getClaimView(parameter, user.getCompany());
                    claimGrid.getSelectionModel().select(row);
                } catch (Exception e) {
                }
            }
        } else {
            claimForm.displayForm(false);
        }

    }
}
