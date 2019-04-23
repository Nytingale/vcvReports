package com.vcv.frontend.grids;

import com.vcv.backend.views.VehicleView;

import com.vaadin.flow.component.grid.Grid;

public class VehicleGrid extends Grid<VehicleView> {
    public  VehicleGrid() {
        this.setSizeFull();

        this.addColumn(VehicleView::getVin).setSortable(true).setHeader("VIN").setFlexGrow(20);
        this.addColumn(VehicleView::getYear).setSortable(true).setHeader("Year").setFlexGrow(5);
        this.addColumn(VehicleView::getMake).setSortable(true).setHeader("Make").setFlexGrow(15);
        this.addColumn(VehicleView::getModel).setSortable(true).setHeader("Model").setFlexGrow(15);
        this.addColumn(VehicleView::getColour).setSortable(true).setHeader("Colour").setFlexGrow(10);
        this.addColumn(VehicleView::getValue).setSortable(true).setHeader("Value").setFlexGrow(5);
        this.addColumn(VehicleView::getDealership).setSortable(true).setHeader("Dealership").setFlexGrow(15);
        this.addColumn(VehicleView::getEvaluationDate).setSortable(true).setHeader("Evaluation Date").setFlexGrow(15);
        this.addColumn(VehicleView::getRegistrationDate).setSortable(true).setHeader("Registration Date").setFlexGrow(15);
        this.addColumn(VehicleView::getManufacturer).setSortable(true).setHeader("Manufacturer").setFlexGrow(30);
        this.addColumn(VehicleView::getTransmission).setSortable(true).setHeader("Transmission").setFlexGrow(15);
        this.addColumn(VehicleView::getFuelType).setSortable(true).setHeader("Fuel Type").setFlexGrow(15);
        this.addColumn(VehicleView::getSteering).setSortable(true).setHeader("Steering").setFlexGrow(10);
        this.addColumn(VehicleView::getMileage).setSortable(true).setHeader("Mileage").setFlexGrow(5);
        this.addColumn(VehicleView::getEngine).setSortable(true).setHeader("Engine").setFlexGrow(5);
        this.addColumn(VehicleView::getDrive).setSortable(true).setHeader("Drive").setFlexGrow(10);
        this.addColumn(VehicleView::getBody).setSortable(true).setHeader("Body").setFlexGrow(5);
        this.addColumn(VehicleView::getNumDoors).setSortable(true).setHeader("Doors").setFlexGrow(5);
        this.addColumn(VehicleView::getNumSeats).setSortable(true).setHeader("Seats").setFlexGrow(5);
        this.addColumn(VehicleView::getWrittenOff).setSortable(true).setHeader("Written Off").setFlexGrow(3);
        this.addColumn(VehicleView::getStolen).setSortable(true).setHeader("Stolen").setFlexGrow(3);
        this.addColumn(VehicleView::getNumAccidents).setSortable(true).setHeader("Accidents").setFlexGrow(5);
        this.addColumn(VehicleView::getNumRobberies).setSortable(true).setHeader("Robberies").setFlexGrow(5);
        this.addColumn(VehicleView::getNumSalvages).setSortable(true).setHeader("Salvages").setFlexGrow(5);
        this.addColumn(VehicleView::getNumServices).setSortable(true).setHeader("Services").setFlexGrow(5);
        this.addColumn(VehicleView::getNumOwners).setSortable(true).setHeader("Owners").setFlexGrow(5);
        this.addColumn(VehicleView::getInsurance).setSortable(true).setHeader("Insurance").setFlexGrow(25);
        this.addColumn(VehicleView::getPolicyNumber).setSortable(true).setHeader("Policy Number").setFlexGrow(25);
    }

    public VehicleView getSelectedRow() {
        return this.asSingleSelect().getValue();
    }
}
