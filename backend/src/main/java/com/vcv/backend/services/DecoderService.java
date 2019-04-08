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
                vehicle.getNumSeats() == null ||
                vehicle.getBody() == null ||
                vehicle.getNumDoors() == null) {
            JSONArray labels = check(vehicle.getVin());
            if(!labels.isEmpty()) {
                JSONArray values = decode(vehicle.getVin());
                if(labels.length() == values.length()) {
                    for(int x = 0; x < values.length(); x++) {

                        JSONObject jsonObject = values.getJSONObject(x);
                        String label = jsonObject.getString("label");

                        switch(label) {
                           case "Transmission (full)":       vehicle.setTransmission(String.valueOf(jsonObject.get("value")));
                           case "Manufacturer":              vehicle.setManufacturer(String.valueOf(jsonObject.get("value")));
                           case "Fuel Type - Primary":       vehicle.setFuelType(String.valueOf(jsonObject.get("value")));
                           case "Steering":                  vehicle.setSteering(String.valueOf(jsonObject.get("value")));
                           case "Engine Displacement (ccm)": vehicle.setEngine(String.valueOf(jsonObject.get("value")));
                           case "Drive":                     vehicle.setDrive(String.valueOf(jsonObject.get("value")));
                           case "Body":                      vehicle.setBody(String.valueOf(jsonObject.get("value")));
                           case "Number of Seats":           vehicle.setNumSeats(Integer.valueOf(String.valueOf(jsonObject.get("value"))));
                           case "Number of Doors":           vehicle.setNumDoors(Integer.valueOf(String.valueOf(jsonObject.get("value"))));
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
            String response = restTemplate.getForObject(uri, String.class);
            if(response != null) {
                JSONObject jsonObject = new JSONObject(response);
                return jsonObject.getJSONArray("decode");
            } else throw new DecoderServiceException("Error 700: check(vin) returned null");
        } else throw new DecoderServiceException("Error 705: check(vin) failed to build a URI from the VIN");
    }

    private JSONArray decode(String vin) throws DecoderServiceException {
        String uri = vinDecoderConfig.decode(vin);
        if (!uri.isEmpty()) {
            String response = restTemplate.getForObject(uri, String.class);
            if (response != null) {
                JSONObject jsonObject = new JSONObject(response);
                return jsonObject.getJSONArray("decode");
            } else throw new DecoderServiceException("Error 700: decode(vin) returned null");
        } else throw new DecoderServiceException("Error 705: decode(vin) failed to build a URI from the VIN");
    }
}
