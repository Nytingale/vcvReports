package com.vcv.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vcv.backend.entities.User;
import com.vcv.backend.views.ClaimView;
import com.vcv.backend.services.ClaimService;

import com.vcv.backend.views.UserView;
import com.vcv.frontend.MainLayout;
import com.vcv.frontend.forms.ClaimForm;
import com.vcv.frontend.grids.ClaimGrid;

import org.springframework.beans.factory.annotation.Autowired;

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
        filter.setPlaceholder("Search by Claim Number, Insurance Company Name");
        filter.addValueChangeListener(event -> searchFilter(event.getValue()));
    }

    private void newClaim() {
        claimGrid.getSelectionModel().deselectAll();
        UI.getCurrent().navigate(InsuranceClaimView.class, "new claim");
        claimForm.displayForm(true);
        claimForm.displayClaim(new ClaimView());
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
