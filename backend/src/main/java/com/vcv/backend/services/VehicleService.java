package com.vcv.backend.services;

import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.configs.FileStorageConfig;
import com.vcv.backend.exceptions.VehicleServiceException;
import com.vcv.backend.repositories.VehicleRepository;

import com.vcv.backend.views.VehicleView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

@Service
public class VehicleService {
    @Autowired
    private FileStorageConfig fileStorageConfig;

    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleView getVehicle(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) return new VehicleView().build(vehicle);
        else throw new VehicleServiceException("Error 500: getVehicle(vin) returned null");
    }

    public VehicleView getVehicle(Integer year,
                                  String make,
                                  String model) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByYearMakeModel(year, make, model);
        if(vehicle != null) return new VehicleView().build(vehicle);
        else throw new VehicleServiceException("Error 500: getVehicle(year, make, model) returned null");
    }

    public Boolean uploadVehicle(String name,
                                 MultipartFile file) throws VehicleServiceException {
        // First, Ensure that the File Storage Location is Created
        Path storageLocation = Paths.get(fileStorageConfig.getUploadDirectory())
                .toAbsolutePath()
                .normalize();
        try {
            Files.createDirectory(storageLocation);
        } catch (IOException e) {
            throw new VehicleServiceException("Error 505: uploadVehicle(name, file) failed to create the directory for File Storage Location");
        }

        // Second, Copy the File to the File Storage Location
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = storageLocation.resolve(filename);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new VehicleServiceException("Error 510: uploadVehicle(name, file) has failed to create the file on the server");
        }

        // Third, Open the File to Read and Extract its Information into the Vehicle Database
        Workbook workbook;
        try {
            if (filename.endsWith("xls")) workbook = new HSSFWorkbook(file.getInputStream());
            else if (filename.endsWith("xlsx")) workbook = new XSSFWorkbook(filename);
            else throw new VehicleServiceException("Error 515: uploadVehicle(name, file) has failed as the file is an invalid type (.xls, .xlsx)");

            for(int x = 0; x < workbook.getNumberOfSheets(); x++) {
                Sheet sheet = workbook.getSheetAt(x);
                Vehicle vehicle = new Vehicle();

                Iterator<Row> rows = sheet.rowIterator();
                while(rows.hasNext()) {
                    Row row = rows.next();
                    Iterator<Cell> cells = row.cellIterator();
                    while(cells.hasNext()) {
                        Cell cell = cells.next();

                    }
                }
            }
        } catch(IOException e) {
            throw new VehicleServiceException("Error 520: uploadVehicle(name, file) has failed to read the file on the server");
        }
    }
}
