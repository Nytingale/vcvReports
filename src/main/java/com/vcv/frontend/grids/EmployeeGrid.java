package com.vcv.frontend.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vcv.backend.entities.User;
import com.vcv.backend.views.UserView;

public class EmployeeGrid extends Grid<UserView> {

    public EmployeeGrid() {
        this.setSizeFull();


    }

    private void resetPassword(User employee) {

    }
}
