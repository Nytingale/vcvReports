package com.vcv.backend.services;

import com.vcv.backend.configs.VinDecoderConfig;
import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.DecoderServiceException;
import com.vcv.backend.utilities.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DecoderService {
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired private VinDecoderConfig vinDecoderConfig;

    public JSONObject checkInfo(String vin) throws DecoderServiceException {
        String uri = vinDecoderConfig.info(vin);
        if(!uri.isEmpty()) {
            JSONObject result = restTemplate.getForObject(uri, JSONObject.class);
            if(result != null) {
                return result;
            } else throw new DecoderServiceException("Error 700: checkInfo(vin) returned null");
        } else throw new DecoderServiceException("Error 705: checkInfo(vin) failed to build a URI from the VIN");
    }

    public JSONObject decode(String vin) throws DecoderServiceException {
        String uri = vinDecoderConfig.decode(vin);
        if (!uri.isEmpty()) {
            JSONObject result = restTemplate.getForObject(uri, JSONObject.class);
            if (result != null) {
                return result;
            } else throw new DecoderServiceException("Error 700: decode(vin) returned null");
        } else throw new DecoderServiceException("Error 705: decode(vin) failed to build a URI from the VIN");
    }

    public JSONObject checkBalance(User vcv) throws DecoderServiceException {
        if(!Utils.isValidStaff(vcv)) throw new DecoderServiceException("Error 720: checkBalance(vcv) has failed you for VCV Staff Authentication");

        String uri = vinDecoderConfig.balance();
        if (!uri.isEmpty()) {
            JSONObject result = restTemplate.getForObject(uri, JSONObject.class);
            if (result != null) {
                return result;
            } else throw new DecoderServiceException("Error 700: checkBalance(vcv) returned null");
        } else throw new DecoderServiceException("Error 705: checkBalance(vcv) failed to build a URI from the VIN");
    }
}
