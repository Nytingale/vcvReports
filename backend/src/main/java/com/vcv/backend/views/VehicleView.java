package com.vcv.backend.views;

public class VehicleView {
    private String vin;
    private Integer year;
    private String make;
    private String model;
    private String manufacturer;
    private String engine;
    private String colour;
    private String mileage;
    private String transmission;
    private String dealership;
    private Integer value;
    private Boolean writtenOff;
    private Boolean stolen;
    private String evaluationDate;
    private String registrationDate;
    private Integer numAccidents;
    private Integer numRobberies;
    private Integer numSalvages;
    private Integer numServices;
    private String insuranceName;
    private String policyNumber;

    public String getVin() {
        return vin;
    }
    public Integer getYear() {
        return year;
    }
    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getEngine() {
        return engine;
    }
    public String getColour() {
        return colour;
    }
    public String getMileage() {
        return mileage;
    }
    public String getTransmission() {
        return transmission;
    }
    public String getDealership() {
        return dealership;
    }
    public Integer getValue() {
        return value;
    }
    public Boolean isWrittenOff() {
        return writtenOff;
    }
    public Boolean isStolen() {
        return stolen;
    }
    public String getEvaluationDate() {
        return evaluationDate;
    }
    public String getRegistrationDate() {
        return registrationDate;
    }
    public Integer getNumAccidents() {
        return numAccidents;
    }
    public Integer getNumRobberies() {
        return numRobberies;
    }
    public Integer getNumSalvages() {
        return numSalvages;
    }
    public Integer getNumServices() {
        return numServices;
    }
    public String getInsuranceName() {
        return insuranceName;
    }
    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }
    public void setMileage(String mileage) {
        this.mileage = mileage;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
    public void setDealership(String dealership) {
        this.dealership = dealership;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    public void setWrittenOff(Boolean writtenOff) {
        this.writtenOff = writtenOff;
    }
    public void setStolen(Boolean stolen) {
        this.stolen = stolen;
    }
    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
    public void setNumAccidents(Integer numAccidents) {
        this.numAccidents = numAccidents;
    }
    public void setNumRobberies(Integer numRobberies) {
        this.numRobberies = numRobberies;
    }
    public void setNumSalvages(Integer numSalvages) {
        this.numSalvages = numSalvages;
    }
    public void setNumServices(Integer numServices) {
        this.numServices = numServices;
    }
    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public static class BasicReport {
        private String vin;
        private Integer year;
        private String make;
        private String model;
        private String manufacturer;
        private String colour;

        public BasicReport vin(String vin) {
            this.vin = vin;
            return this;
        }
        public BasicReport year(Integer year) {
            this.year = year;
            return this;
        }
        public BasicReport make(String make) {
            this.make = make;
            return this;
        }
        public BasicReport model(String model) {
            this.model = model;
            return this;
        }
        public BasicReport manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }
        public BasicReport colour(String colour) {
            this.colour = colour;
            return this;
        }

        public BasicReport() {}
        public BasicReport build() {
            BasicReport view = new BasicReport();

            view.vin = vin;
            view.year = year;
            view.make = make;
            view.model = model;
            view.manufacturer = manufacturer;
            view.colour = colour;

            return view;
        }
    }
}
