package com.vcv.backend.services;

import com.vcv.backend.configs.VinDecoderConfig;
import com.vcv.backend.entities.User;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.DecoderServiceException;
import com.vcv.backend.utilities.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DecoderService {
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired private VinDecoderConfig vinDecoderConfig;

    Vehicle updateVehicle(Vehicle vehicle) throws DecoderServiceException {
        if(vehicle.getManufacturer() == null ||
                vehicle.getTransmission() == null ||
                vehicle.getFuelType() == null ||
                vehicle.getSteering() == null ||
                vehicle.getColour() == null ||
                vehicle.getEngine() == null ||
                vehicle.getDrive() == null ||
                vehicle.getSeats() == null ||
                vehicle.getBody() == null ||
                vehicle.getDoors() == null) {
            JSONArray labels = check(vehicle.getVin());
            if(!labels.isEmpty()) {
                JSONArray values = decode(vehicle.getVin());
                if(labels.length() == values.length()) {
                    for(int x = 0; x < values.length(); x++) {
                       String label = labels.getString(x);
                       Object value = values.getJSONObject(x).get(label);

                       switch(label) {
                           case "Transmission (full)":       vehicle.setTransmission(String.valueOf(value).replaceAll(" ", ""));
                           case "Manufacturer":              vehicle.setManufacturer(String.valueOf(value).replaceAll(" ", ""));
                           case "Fuel Type - Primary":       vehicle.setFuelType(String.valueOf(value).replaceAll(" ", ""));
                           case "Steering":                  vehicle.setSteering(String.valueOf(value).replaceAll(" ", ""));
                           case "Engine Displacement (ccm)": vehicle.setEngine(String.valueOf(value).replaceAll(" ", ""));
                           case "Drive":                     vehicle.setDrive(String.valueOf(value).replaceAll(" ", ""));
                           case "Body":                      vehicle.setBody(String.valueOf(value).replaceAll(" ", ""));
                           case "Number of Seats":           vehicle.setSeats(Integer.valueOf(String.valueOf(value).replaceAll(" ", "")));
                           case "Number of Doors":           vehicle.setDoors(Integer.valueOf(String.valueOf(value).replaceAll(" ", "")));
                           default:                          break;
                       }
                    }

                    return vehicle;
                } else throw new DecoderServiceException("Error 710: updateVehicle(vehicle) has found a difference in length of VIN Information versus what is Available");
            } else throw new DecoderServiceException("Error 700: updateVehicle(vehicle) has returned null");
        } else return vehicle;
    }

    List<Vehicle> updateVehicles(List<Vehicle> vehicles) throws DecoderServiceException {
        List<Vehicle> result = new ArrayList<>();

        for(Vehicle vehicle: vehicles) {
            vehicle = updateVehicle(vehicle);
            result.add(vehicle);
        }

        return result;
    }

    JSONObject balance(User vcv) throws DecoderServiceException {
        if(!Utils.isValidStaff(vcv)) throw new DecoderServiceException("Error 720: checkBalance(vcv) has failed you for VCV Staff Authentication");

        String uri = vinDecoderConfig.balance();
        if (!uri.isEmpty()) {
            JSONObject response = restTemplate.getForObject(uri, JSONObject.class);
            if (response != null) {
                return response;
            } else throw new DecoderServiceException("Error 700: checkBalance(vcv) returned null");
        } else throw new DecoderServiceException("Error 705: checkBalance(vcv) failed to build a URI from the VIN");
    }

    private JSONArray check(String vin) throws DecoderServiceException {
        String uri = vinDecoderConfig.info(vin);
        if(!uri.isEmpty()) {
            JSONObject response = restTemplate.getForObject(uri, JSONObject.class);
            if(response != null) {
                return response.getJSONArray("decode");
            } else throw new DecoderServiceException("Error 700: check(vin) returned null");
        } else throw new DecoderServiceException("Error 705: check(vin) failed to build a URI from the VIN");
    }

    private JSONArray decode(String vin) throws DecoderServiceException {
        String uri = vinDecoderConfig.decode(vin);
        if (!uri.isEmpty()) {
            JSONObject response = restTemplate.getForObject(uri, JSONObject.class);
            if (response != null) {
                return response.getJSONArray("decpde");
            } else throw new DecoderServiceException("Error 700: decode(vin) returned null");
        } else throw new DecoderServiceException("Error 705: decode(vin) failed to build a URI from the VIN");
    }
}
