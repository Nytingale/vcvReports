package com.vcv.frontend.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vcv.backend.views.PolicyView;

public class PolicyGrid extends Grid<PolicyView> {
    public PolicyGrid() {
        this.addColumn(PolicyView::getPolicyNumber).setSortable(true).setHeader("Policy Number").setFlexGrow(25);
        this.addColumn(PolicyView::getDate).setSortable(true).setHeader("Date").setFlexGrow(15);
        this.addColumn(PolicyView::getFinancer).setSortable(true).setHeader("Financed By").setFlexGrow(25);
        this.addColumn(PolicyView::getVin).setSortable(true).setHeader("Vehincle VIN").setFlexGrow(20);
        this.addColumn(PolicyView::getValid).setSortable(true).setHeader("Valid").setFlexGrow(3);
        this.addColumn(PolicyView::getCompany).setSortable(false).setHeader("Company").setFlexGrow(25);
    }

    public PolicyView getSelectedRow() {
        return this.asSingleSelect().getValue();
    }
}
