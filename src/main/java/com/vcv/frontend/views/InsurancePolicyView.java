package com.vcv.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.*;
import com.vcv.backend.entities.Policy;
import com.vcv.backend.exceptions.PolicyServiceException;
import com.vcv.backend.services.PolicyService;
import com.vcv.backend.views.PolicyView;
import com.vcv.backend.views.UserView;
import com.vcv.frontend.MainLayout;
import com.vcv.frontend.forms.PolicyForm;
import com.vcv.frontend.grids.PolicyGrid;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "Policies", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class InsurancePolicyView  extends HorizontalLayout implements HasUrlParameter<String> {
    private UserView user;

    private Button newPolicy;

    private PolicyForm policyForm;
    private PolicyGrid policyGrid;

    private ListDataProvider<PolicyView> dataProvider;

    @Autowired
    private PolicyService policyService;

    private InsurancePolicyView() {
        this.setSizeFull();

        TextField filter = new TextField();
        filter.setPlaceholder("Search by Policy Number, Date, or VIN");
        filter.addValueChangeListener(event -> searchFilter(event.getValue()));

        newPolicy = new Button("New Policy");
        newPolicy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newPolicy.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newPolicy.addClickListener(click -> newPolicy());

        try {
            List<PolicyView> policies = policyService.getInsurancePolicies(user.getCompany());
            dataProvider = DataProvider.ofCollection(policies);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        policyGrid = new PolicyGrid();
        policyGrid.setDataProvider(dataProvider);
        policyGrid.asSingleSelect().addValueChangeListener(event -> openInsurancePolicyDetails(event.getValue()));

        policyForm = new PolicyForm("insurance-claim");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(filter);
        verticalLayout.add(policyGrid);
        verticalLayout.setFlexGrow(1, policyGrid);
        verticalLayout.setFlexGrow(0, filter);
        verticalLayout.setSizeFull();
        verticalLayout.expand(policyGrid);

        this.add(verticalLayout);
        this.add(policyForm);
    }

    private void newPolicy() {
        policyGrid.getSelectionModel().deselectAll();
        UI.getCurrent().navigate(InsurancePolicyView.class, "new policy");
        openInsurancePolicyDetails(new PolicyView());
    }

    private void savePolicy(PolicyView policyView) {
        Policy policy = new Policy.Builder().build(policyView);
        policyGrid.getSelectionModel().deselectAll();
        if(policyForm.binder().writeBeanIfValid(policyForm.currentPolicy())) {
            try {
                if (policy.getPolicyNumber() == null) {
                    Notification.show(policyService.add(policy).getMessage());
                    dataProvider.refreshAll();
                }
            } catch (PolicyServiceException e) {
                e.printStackTrace();
            }
        }

        policyForm.displayForm(false);
    }

    private void openInsurancePolicyDetails(PolicyView policy) {
        policyForm.saveBtn().addClickListener(click -> savePolicy(policy));
        policyForm.displayForm(true);
        policyForm.displayPolicy(policy);
    }

    private void searchFilter(String filter) {
        dataProvider.setFilter(policyView ->
                policyView.getVin().contains(filter) ||
                policyView.getDate().contains(filter) ||
                policyView.getPolicyNumber().contains(filter)
        );
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent,
                             @OptionalParameter String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            if (parameter.equals("new policy")) {
                newPolicy();
            } else {
                try {
                    PolicyView row = policyService.getPolicyView(parameter, user.getCompany());
                    policyGrid.getSelectionModel().select(row);
                } catch (Exception e) {
                }
            }
        } else {
            policyForm.displayForm(false);
        }
    }
}
