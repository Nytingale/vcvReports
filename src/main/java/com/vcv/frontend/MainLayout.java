package com.vcv.frontend;

import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

import com.vcv.frontend.components.Menu;

@Theme(value = Lumo.class)
@PWA(name = "Vehicle Curriculum Vitae", shortName = "VCV")
public class MainLayout extends FlexLayout implements RouterLayout {
    private Menu menu;

}
