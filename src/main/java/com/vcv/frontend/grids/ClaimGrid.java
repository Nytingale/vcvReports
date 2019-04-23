package com.vcv.frontend.grids;

import com.vaadin.flow.component.grid.Grid;
import com.vcv.backend.views.ClaimView;

public class ClaimGrid extends Grid<ClaimView> {
    public ClaimGrid() {
        this.addColumn(ClaimView::getClaimNumber).setSortable(true).setHeader("Claim Number").setFlexGrow(25);
        this.addColumn(ClaimView::getType).setSortable(true).setHeader("Type").setFlexGrow(10);
        this.addColumn(ClaimView::getDate).setSortable(true).setHeader("Date").setFlexGrow(15);
        this.addColumn(ClaimView::getPolicyNumber).setSortable(true).setHeader("Policy Number").setFlexGrow(25);
        this.addColumn(ClaimView::getVin).setSortable(true).setHeader("Vehincle VIN").setFlexGrow(20);
        this.addColumn(ClaimView::getCompany).setSortable(false).setHeader("Company").setFlexGrow(25);
    }

    public ClaimView getSelectedRow() {
        return this.asSingleSelect().getValue();
    }
}
