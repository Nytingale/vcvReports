package com.vcv.frontend.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vcv.backend.views.UserView;

public class UserGrid extends Grid<UserView> {
    public UserGrid() {
        this.addColumn(UserView::getEmail).setSortable(true).setHeader("Email").setFlexGrow(20);
        this.addColumn(UserView::getRole).setSortable(true).setHeader("Role").setFlexGrow(5);
        this.addColumn(UserView::getCompany).setSortable(false).setHeader("Company").setFlexGrow(25);
    }

    public UserView getSelectedRow() {
        return this.asSingleSelect().getValue();
    }
}
