package com.vcv.frontend.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.component.checkbox.Checkbox;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BeanValidationBinder;

import com.vaadin.flow.data.value.ValueChangeMode;

import com.vaadin.flow.router.HasUrlParameter;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.views.VehicleView;
import com.vcv.frontend.views.InsuredVehicleView;

public class VehicleForm extends Div {
    private VerticalLayout content;

    private TextField vin;
    private TextField year;
    private TextField make;
    private TextField model;
    private TextField colour;
    private TextField value;
    private TextField dealership;
    private TextField evaluationDate;
    private TextField registrationDate;
    private TextField manufacturer;
    private TextField transmission;
    private TextField fuelType;
    private TextField steering;
    private TextField mileage;
    private TextField engine;
    private TextField drive;
    private TextField body;
    private TextField numDoors;
    private TextField numSeats;
    private Checkbox writtenOff;
    private Checkbox stolen;
    private TextField numAccidents;
    private TextField numRobberies;
    private TextField numSalvages;
    private TextField numServices;
    private TextField numOwners;
    private TextField insurance;
    private TextField policyNumber;

    private Button close;

    private String window;

    private VehicleView currentVehicle;
    private Binder<VehicleView> binder;

    public VehicleForm(String view) {
        this.window = view;
        this.setClassName(window + "-form");

        content = new VerticalLayout();
        content.setSizeUndefined();

        vin = new TextField("Vin");
        vin.setRequired(true);
        vin.setValueChangeMode(ValueChangeMode.EAGER);

        year = new TextField("Year");
        year.setRequired(true);
        year.setValueChangeMode(ValueChangeMode.EAGER);

        make = new TextField("Make");
        make.setRequired(true);
        make.setValueChangeMode(ValueChangeMode.EAGER);

        model = new TextField("Model");
        model.setRequired(true);
        model.setValueChangeMode(ValueChangeMode.EAGER);

        colour = new TextField("Colour");
        colour.setValueChangeMode(ValueChangeMode.EAGER);

        value = new TextField("Value");
        value.setRequired(true);
        value.setPrefixComponent(new Span("$"));
        value.setValueChangeMode(ValueChangeMode.EAGER);

        dealership = new TextField("Dealership");
        dealership.setRequired(true);
        dealership.setValueChangeMode(ValueChangeMode.EAGER);

        evaluationDate = new TextField("Evaluation Date");
        evaluationDate.setValueChangeMode(ValueChangeMode.EAGER);
        evaluationDate.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);

        registrationDate = new TextField("Registration Date");
        registrationDate.setValueChangeMode(ValueChangeMode.EAGER);
        registrationDate.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);

        manufacturer = new TextField("Manufacturer");
        manufacturer.setValueChangeMode(ValueChangeMode.EAGER);

        transmission = new TextField("Transmission");
        transmission.setValueChangeMode(ValueChangeMode.EAGER);

        fuelType = new TextField("Fuel Type");
        fuelType.setValueChangeMode(ValueChangeMode.EAGER);

        steering = new TextField("Steering");
        steering.setValueChangeMode(ValueChangeMode.EAGER);

        mileage = new TextField("Mileage");
        mileage.setSuffixComponent(new Span("km"));
        mileage.setValueChangeMode(ValueChangeMode.EAGER);

        engine = new TextField("Engine");
        engine.setValueChangeMode(ValueChangeMode.EAGER);

        drive = new TextField("Drive");
        drive.setValueChangeMode(ValueChangeMode.EAGER);

        body = new TextField("Body");
        body.setValueChangeMode(ValueChangeMode.EAGER);

        numDoors = new TextField("Number of Doors");
        numDoors.setValueChangeMode(ValueChangeMode.EAGER);
        numDoors.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);

        numSeats = new TextField("Number of Seats");
        numSeats.setValueChangeMode(ValueChangeMode.EAGER);
        numSeats.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);

        writtenOff = new Checkbox("Written Off");

        stolen = new Checkbox("Stolen");

        numAccidents = new TextField("Number of Accidents");
        numAccidents.setValueChangeMode(ValueChangeMode.EAGER);
        numAccidents.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);

        numRobberies = new TextField("Number of Robberies");
        numRobberies.setValueChangeMode(ValueChangeMode.EAGER);
        numRobberies.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);

        numSalvages = new TextField("Number of Salvages");
        numSalvages.setValueChangeMode(ValueChangeMode.EAGER);
        numSalvages.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);

        numServices = new TextField("Number of Services");
        numServices.setValueChangeMode(ValueChangeMode.EAGER);
        numServices.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);

        numOwners = new TextField("Number of Owners");
        numOwners.setValueChangeMode(ValueChangeMode.EAGER);
        numOwners.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);

        insurance = new TextField("Insurance Company");
        insurance.setValueChangeMode(ValueChangeMode.EAGER);

        policyNumber = new TextField("Policy Number");
        policyNumber.setValueChangeMode(ValueChangeMode.EAGER);

        close = new Button("Close");
        close.setWidth("100%");
        close.addClickListener(event -> closeForm());

        binder = new BeanValidationBinder<>(VehicleView.class);
        binder.bindInstanceFields(this);

        VerticalLayout vinColour = new VerticalLayout(vin, colour);
        vinColour.setFlexGrow(1, vin, colour);
        vinColour.setWidth("100%");

        VerticalLayout yearMakeModel = new VerticalLayout(year, make, model);
        yearMakeModel.setFlexGrow(1, year, make, model);
        yearMakeModel.setWidth("100%");

        HorizontalLayout horizontalLayout1 = new HorizontalLayout(vinColour, yearMakeModel);
        horizontalLayout1.setFlexGrow(1, vinColour, yearMakeModel);
        horizontalLayout1.setWidth("100%");

        HorizontalLayout horizontalLayout2 = new HorizontalLayout(colour, manufacturer);
        horizontalLayout2.setFlexGrow(1, colour, manufacturer);
        horizontalLayout2.setWidth("100%");

        HorizontalLayout horizontalLayout3 = new HorizontalLayout(dealership, value);
        horizontalLayout3.setFlexGrow(1, dealership, value);
        horizontalLayout3.setWidth("100%");

        HorizontalLayout horizontalLayout4 = new HorizontalLayout(evaluationDate, registrationDate);
        horizontalLayout4.setFlexGrow(1, evaluationDate, registrationDate);
        horizontalLayout4.setWidth("100%");

        HorizontalLayout horizontalLayout5 = new HorizontalLayout(transmission, fuelType);
        horizontalLayout5.setFlexGrow(1, transmission, fuelType);
        horizontalLayout5.setWidth("100%");

        HorizontalLayout horizontalLayout6 = new HorizontalLayout(engine, mileage);
        horizontalLayout6.setFlexGrow(1, engine, mileage);
        horizontalLayout6.setWidth("100%");

        HorizontalLayout horizontalLayout7 = new HorizontalLayout(steering, drive, body);
        horizontalLayout7.setFlexGrow(1, steering, drive, body);
        horizontalLayout7.setWidth("100%");

        HorizontalLayout horizontalLayout8 = new HorizontalLayout(numDoors, numSeats);
        horizontalLayout8.setFlexGrow(1, numDoors, numSeats);
        horizontalLayout8.setWidth("100%");

        HorizontalLayout horizontalLayout9 = new HorizontalLayout(writtenOff, stolen);
        horizontalLayout9.setFlexGrow(1, writtenOff, stolen);
        horizontalLayout9.setWidth("100%");

        HorizontalLayout horizontalLayout10 = new HorizontalLayout(numAccidents, numRobberies, numSalvages);
        horizontalLayout10.setFlexGrow(1, numAccidents, numRobberies, numSalvages);
        horizontalLayout10.setWidth("100%");

        HorizontalLayout horizontalLayout11 = new HorizontalLayout(numServices, numOwners);
        horizontalLayout11.setFlexGrow(1, numServices, numOwners);
        horizontalLayout11.setWidth("100%");

        content.add(
                horizontalLayout1, horizontalLayout2, horizontalLayout3, horizontalLayout4, horizontalLayout5,
                horizontalLayout6, horizontalLayout7, horizontalLayout8, horizontalLayout9, horizontalLayout10,
                horizontalLayout11, close);

        this.add(content);
    }

    private void closeForm() {
        switch(window) {
            case "insured-vehicle":
                UI.getCurrent().navigate(InsuredVehicleView.class, "");
                break;

            case "registered-vehicle":
                //UI.getCurrent().navigate(RegisteredVehicleView.class, "");
                break;
        }
        displayForm(false);
    }

    public void addButton(Button btn) {
        content.add(btn);
    }

    public void removeButton(Button btn) {
        content.remove(btn);
    }

    public void displayForm(boolean flag) {
        this.setVisible(flag);
        this.setEnabled(flag);
    }

    public void displayVehicle(VehicleView vehicle) {
        currentVehicle = vehicle;
        binder.readBean(vehicle);
    }
}
