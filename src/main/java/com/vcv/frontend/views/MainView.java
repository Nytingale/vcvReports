package com.vcv.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route("")
@PWA(name = "VCV Frontend Demo", shortName = "Demo")
public class MainView extends VerticalLayout {

    private UserServ

    public MainView() {
        Button button = new Button("Click me", event -> Notification.show("Clicked!"));
        add(button);
    }
}
