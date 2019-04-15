package com.vcv.frontend.screens;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AboutScreen extends VerticalLayout {
    public Span content;

    public AboutScreen() {
        content = new Span("This is a placeholder for future reference");
        this.setJustifyContentMode(JustifyContentMode.START);
        this.add(VaadinIcon.INFO_CIRCLE.create());
        this.setAlignItems(Alignment.START);
        this.setSizeFull();
        this.add(content);
    }
}
