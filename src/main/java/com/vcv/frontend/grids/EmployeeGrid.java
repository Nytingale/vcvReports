package com.vcv.frontend.grids;

import com.vcv.backend.views.UserView;

import com.vaadin.flow.component.grid.Grid;

public class EmployeeGrid extends Grid<UserView> {

    public EmployeeGrid() {
        this.setSizeFull();

        this.addColumn(UserView::getCompany)
                .setHeader("Company")
                .setFlexGrow(10);
        this.addColumn(UserView::getEmail)
                .setHeader("Email")
                .setSortable(true)
                .setFlexGrow(20);
        this.addColumn(UserView::getRole)
                .setHeader("Role")
                .setFlexGrow(5);
    }


}
