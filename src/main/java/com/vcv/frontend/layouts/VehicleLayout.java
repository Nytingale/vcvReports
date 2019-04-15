package com.vcv.frontend.layouts;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vcv.backend.entities.Vehicle;

public class VehicleLayout extends HorizontalLayout {
    protected TextField vin;
    protected TextField year;
    protected TextField make;
    protected TextField model;
    protected TextField colour;
    protected TextField value;
    protected TextField dealership;
    protected TextField evaluationDate;
    protected TextField registrationDate;
    protected TextField manufacturer;
    protected TextField transmission;
    protected TextField fuelType;
    protected TextField steering;
    protected TextField mileage;
    protected TextField engine;
    protected TextField drive;
    protected TextField body;
    protected TextField numDoors;
    protected TextField numSeats;
    protected Checkbox writtenOff;
    protected Checkbox stolen;
    protected TextField numAccidents;
    protected TextField numRobberies;
    protected TextField numSalvages;
    protected TextField numServices;
    protected TextField numOwners;
    protected TextField insurance;
    protected TextField policyNumber;

    private Binder<Vehicle> binder;
}
