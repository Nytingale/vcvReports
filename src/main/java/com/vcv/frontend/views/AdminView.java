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
import com.vaadin.navigator.View;
import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.spring.annotation.SpringView;
import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.CompanyServiceException;
import com.vcv.backend.exceptions.UserServiceException;
import com.vcv.backend.services.CompanyService;
import com.vcv.backend.services.FileService;
import com.vcv.backend.services.UserService;
import com.vcv.backend.views.UserView;
import com.vcv.frontend.MainLayout;
import com.vcv.frontend.forms.UserForm;
import com.vcv.frontend.grids.UserGrid;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringView(name = "Admin")
@Route(value = "Admin", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AdminView extends HorizontalLayout implements View, ViewAccessControl, HasUrlParameter<String> {
    private UserView user;

    private UserForm employeeForm;
    private UserGrid employeeGrid;

    private Button newEmployee;
    private Button changeCompanyLogo;
    private Button renewSubscription;
    private Button cancelSubscription;

    private ListDataProvider<UserView> dataProvider;

    @Autowired private FileService fileService;
    @Autowired private UserService userService;
    @Autowired private CompanyService companyService;

    public AdminView() {
        this.setSizeFull();

        TextField filter = new TextField();
        filter.setPlaceholder("Search by Email");
        filter.addValueChangeListener(event -> searchFilter(event.getValue()));

        newEmployee = new Button("New");
        newEmployee.addClickListener(click -> newEmployee());
        newEmployee.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newEmployee.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        changeCompanyLogo = new Button("Change Company Logo");
        changeCompanyLogo.addClickListener(click -> changeCompanyLogo());
        changeCompanyLogo.setIcon(VaadinIcon.UPLOAD.create());
        changeCompanyLogo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        renewSubscription = new Button("Renew Company Subscription");
        renewSubscription.addClickListener(click -> renewSubscription());
        renewSubscription.setIcon(VaadinIcon.REFRESH.create());
        renewSubscription.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelSubscription = new Button("Cancel Company Subscription");
        cancelSubscription.addClickListener(click -> cancelSubscription());
        cancelSubscription.setIcon(VaadinIcon.STOP.create());
        cancelSubscription.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        try {
            List<UserView> employees = userService.getEmployees(user.getEmail());
            dataProvider = DataProvider.ofCollection(employees);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        employeeGrid = new UserGrid();
        employeeGrid.setDataProvider(dataProvider);
        employeeGrid.asSingleSelect().addValueChangeListener(event -> openEmployeeDetails(event.getValue()));

        employeeForm = new UserForm("company-employees");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.add(newEmployee);
        horizontalLayout.add(changeCompanyLogo);
        horizontalLayout.add(renewSubscription);
        horizontalLayout.add(cancelSubscription);
        horizontalLayout.expand(newEmployee);
        horizontalLayout.setVerticalComponentAlignment(Alignment.START, newEmployee);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(filter);
        verticalLayout.add(horizontalLayout);
        verticalLayout.add(employeeGrid);
        verticalLayout.setFlexGrow(2, employeeGrid);
        verticalLayout.setFlexGrow(1, horizontalLayout);
        verticalLayout.setFlexGrow(0, filter);
        verticalLayout.setSizeFull();
        verticalLayout.expand(employeeGrid);

        this.add(verticalLayout);
        this.add(employeeForm);
    }

    private void newEmployee() {
        employeeGrid.getSelectionModel().deselectAll();
        UI.getCurrent().navigate(InsuranceClaimView.class, "new employee");
        openEmployeeDetails(new UserView());
    }

    private void makeAdmin(UserView employeeView) {
        User employee = new User.Builder().build(employeeView);
        employeeGrid.getSelectionModel().deselectAll();
        if(employeeForm.binder().writeBeanIfValid(employeeForm.currentUser())) {
            try {
                if (employee.getEmail() == null && employee.getRoleId() < 2L && employee.getCompanyId().equals(user.getCompanyId())) {
                    Notification.show(userService.changeAdmin(new User.Builder().build(user), employee).getMessage());
                    dataProvider.refreshItem(employeeForm.currentUser());
                }
            } catch (UserServiceException e) {
                e.printStackTrace();
            }
        }

        employeeForm.displayForm(false);
    }

    private void resetPassword(UserView employeeView) {
        User employee = new User.Builder().build(employeeView);
        employeeGrid.getSelectionModel().deselectAll();
        if(employeeForm.binder().writeBeanIfValid(employeeForm.currentUser())) {
            try {
                if (employee.getEmail() == null && employee.getRoleId() < 2L && employee.getCompanyId().equals(user.getCompanyId())) {
                    Notification.show(userService.resetPassword(new User.Builder().build(user), employee.getEmail()).getMessage());
                    dataProvider.refreshItem(employeeForm.currentUser());
                }
            } catch (UserServiceException e) {
                e.printStackTrace();
            }
        }

        employeeForm.displayForm(false);
    }

    private void removeEmployee(UserView employeeView) {
        User employee = new User.Builder().build(employeeView);
        employeeGrid.getSelectionModel().deselectAll();
        if(employeeForm.binder().writeBeanIfValid(employeeForm.currentUser())) {
            try {
                if (employee.getEmail() == null && employee.getRoleId() < 2L && employee.getCompanyId().equals(user.getCompanyId())) {
                    Notification.show(userService.removeEmployee(new User.Builder().build(user), employee.getEmail()).getMessage());
                    dataProvider.refreshAll();
                }
            } catch (UserServiceException e) {
                e.printStackTrace();
            }
        }

        employeeForm.displayForm(false);
    }

    private void changeCompanyLogo() {
/*        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");

        upload.addSucceededListener(event -> {
            File image = new File(event.getFileName());
            InputStream stream = buffer.getInputStream(event.getFileName());
            FileUtils.copyInputStreamToFile(stream, image);

            fileService.uploadImage(user, image, request);
        });*/
    }

    private void renewSubscription() {
        try {
            Notification.show(companyService.renewSubscription(new User.Builder().build(user)).getMessage());
            dataProvider.refreshItem(employeeForm.currentUser());
        } catch (CompanyServiceException e) {
            e.printStackTrace();
        }
    }

    private void cancelSubscription() {
        try {
            Notification.show(companyService.cancelSubscription(new User.Builder().build(user)).getMessage());
            dataProvider.refreshItem(employeeForm.currentUser());
        } catch (CompanyServiceException e) {
            e.printStackTrace();
        }
    }

    private void openEmployeeDetails(UserView employee) {
        employeeForm.makeAdminBtn().addClickListener(click -> makeAdmin(employee));
        employeeForm.resetPasswordBtn().addClickListener(click -> resetPassword(employee));
        employeeForm.removeEmployeeBtn().addClickListener(click -> removeEmployee(employee));
        employeeForm.displayForm(true);
        employeeForm.displayUser(employee);
    }

    private void searchFilter(String filter) {
        dataProvider.setFilter(userView -> userView.getEmail().contains(filter));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent,
                             @OptionalParameter String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            if (parameter.equals("new employee")) {
                newEmployee();
            } else if (parameter.equals("change company logo")) {
                changeCompanyLogo();
            } else if (parameter.equals("renew company subscription")) {
                renewSubscription();
            } else if (parameter.equals("cancel company subscription")) {
                cancelSubscription();
            } else {
                try {
                    UserView row = userService.getUserView(parameter);
                    employeeGrid.getSelectionModel().select(row);
                } catch (Exception e) {
                }
            }
        } else {
            employeeForm.displayForm(false);
        }
    }

    @Override
    public boolean isAccessGranted(com.vaadin.ui.UI ui, String s) {
        return true;
    }
}
