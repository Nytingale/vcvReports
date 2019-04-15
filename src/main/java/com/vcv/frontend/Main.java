package com.vcv.frontend;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vcv.frontend.components.Menu;
import com.vcv.frontend.screens.*;

@Theme(value = Lumo.class)
@PWA(name = "Vehicle Curriculum Vitae", shortName = "VCV")
public class Main extends FlexLayout implements RouterLayout {
    private Menu menu;

    public Main() {
        this.setSizeFull();
        this.setClassName("main");

        // Populating the Menu
        menu = new Menu();
        menu.addView(HomeScreen.class, "Home", VaadinIcon.HOME.create());
        menu.addView(PartnersScreen.class, "Partners", VaadinIcon.HANDSHAKE.create());
        menu.addView(AboutScreen.class, "About Us", VaadinIcon.INFO_CIRCLE.create());
        menu.addView(FAQScreen.class, "FAQ", VaadinIcon.QUESTION_CIRCLE.create());
        this.add(menu);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // Grabbing the Current Session Scope Configuration
        RouteConfiguration sessionScopedConfiguration = RouteConfiguration.forSessionScope();

        // Adding the Menu Button Depending on the Client Type that's Logged In
        if (sessionScopedConfiguration.isRouteRegistered(VCVScreen.class))
            menu.addView(VCVScreen.class, "VCV", VaadinIcon.BUILDING.create());
        else if (sessionScopedConfiguration.isRouteRegistered(GarageScreen.class))
            menu.addView(GarageScreen.class, "Garage", VaadinIcon.HAMMER.create());
        else if (sessionScopedConfiguration.isRouteRegistered(DealershipScreen.class))
            menu.addView(DealershipScreen.class, "Dealership", VaadinIcon.CAR.create());
        else if (sessionScopedConfiguration.isRouteRegistered(InsuranceScreen.class))
            menu.addView(InsuranceScreen.class, "Insurance", VaadinIcon.CLIPBOARD.create());

        // Adding the Menu Button for Admins
        if (sessionScopedConfiguration.isRouteRegistered(AdminScreen.class))
            menu.addView(AdminScreen.class, "Admin", VaadinIcon.BRIEFCASE.create());
    }
}
