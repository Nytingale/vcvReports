package com.vcv.frontend;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouteBaseData;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

import com.vcv.frontend.components.Menu;
import com.vcv.frontend.views.AdminView;
import com.vcv.frontend.views.HomeView;

@HtmlImport("css/shared-styles.html")
@Theme(value = Lumo.class)
@PWA(name = "Vehicle Curriculum Vitae", shortName = "VCV")
public class MainLayout extends FlexLayout implements RouterLayout {
    private Menu menu;

    public MainLayout() {
        this.setSizeFull();
        this.setClassName("main-layout");

        menu = new Menu();
        menu.addView(HomeView.class, "VCV", VaadinIcon.HOME.create());

        this.add(menu);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // add the admin view menu item if/when it is registered dynamically
        Command addAdminMenuItemCommand = () -> menu.addView(AdminView.class, "Admin", VaadinIcon.DOCTOR.create());
        RouteConfiguration sessionScopedConfiguration = RouteConfiguration.forSessionScope();
        if (sessionScopedConfiguration.isRouteRegistered(AdminView.class)) {
            addAdminMenuItemCommand.execute();
        } else {
            sessionScopedConfiguration.addRoutesChangeListener(event -> {
                for (RouteBaseData data : event.getAddedRoutes()) {
                    if (data.getNavigationTarget().equals(AdminView.class)) {
                        addAdminMenuItemCommand.execute();
                    }
                }
            });
        }
    }
}
