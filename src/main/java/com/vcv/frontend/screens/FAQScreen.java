package com.vcv.frontend.screens;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class FAQScreen extends VerticalLayout {
    public Span content;

    public FAQScreen() {
        content = new Span("This is a placeholder for future reference");
        this.setJustifyContentMode(JustifyContentMode.START);
        this.add(VaadinIcon.QUESTION_CIRCLE.create());
        this.setAlignItems(Alignment.START);
        this.setSizeFull();
        this.add(content);
    }
}
