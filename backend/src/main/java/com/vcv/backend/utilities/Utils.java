package com.vcv.backend.utilities;

import com.vcv.backend.entities.Policy;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.enums.CompanyType;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Arrays;

public class Utils {
    public static String isValidVin(String vin) {
        if(vin == null || vin.trim().isEmpty()) return null;
        else if(vin.length() == 17) return vin;
        else return null;
    }

    public static String isValidEmail(String email) {
        if(email == null || email.trim().isEmpty()) return null;
        else if(email.length() > 4 && email.length() < 65 && email.matches("-^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$")) return email;
        else return null;
    }

    public static String isValidPassword(String password) {
        if(password == null || password.trim().isEmpty()) return null;
        else if(password.length() > 8 && password.length() <= 32) return password;
        else return null;
    }

    public static String isValidString(String string) {
        if(string == null || string.trim().isEmpty()) return null;
        else return string;
    }

    public static Integer isValidYear(String year) {
        if(year == null || year.trim().isEmpty()) return null;
        else if(year.matches("-?[0-9]+") && Integer.parseInt(year) > LocalDate.now().getYear()) return Integer.parseInt(year);
        else return null;
    }

    public static Object isValidEntity(Object entity) {
        if(entity != null) {
            Field[] entityFields = entity.getClass().getDeclaredFields();
            if(entity instanceof Vehicle) {
                Field[] vehicleFields = Vehicle.class.getDeclaredFields();
                if(Arrays.equals(entityFields, vehicleFields)) return entity;
                else return null;
            } else if(entity instanceof Policy) {
                Field[] policyFields = Policy.class.getDeclaredFields();
                if(Arrays.equals(entityFields, policyFields)) return entity;
                else return null;
            }
        }
        return null;
    }

    public static MultipartFile isValidExcelFile(MultipartFile file) {
        if(file == null) return null;
        else {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if(Objects.equals(extension, "xls") || Objects.equals(extension, "xlsx")) return file;
            else return null;
        }
    }

    public static String isValidSubscribingCompany(String name, String type) {
        if(name == null || name.trim().isEmpty() || type == null || type.trim().isEmpty()) return null;
        else if(CompanyType.valueOf(type).level() > 1) return name;
        else return null;
    }
}
