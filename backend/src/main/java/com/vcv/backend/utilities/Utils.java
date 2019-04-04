package com.vcv.backend.utilities;

import com.google.common.base.CaseFormat;
import com.vcv.backend.entities.*;
import com.vcv.backend.enums.CompanyType;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Arrays;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static MultipartFile isValidImage(MultipartFile img) {
        if(img == null) return null;
        else if(Objects.requireNonNull(img.getContentType()).matches("^(jpe?g|png|bmp)$")) return img;
        return null;
    }

    public static Boolean isValidStaff(User vcv) {
        if(vcv == null) return false;
        else return vcv.getRoleId().equals(3L);
    }

    public static Boolean isValidStaffOrAdmin(User user) {
        if(user == null) return false;
        else return user.getRoleId() > 1L;
    }

    public static String isValidVin(String vin) {
        if(vin == null || vin.trim().isEmpty()) return null;
        else if(vin.toUpperCase().contains("I") || vin.toUpperCase().contains("O") || vin.toUpperCase().contains("Q")) return null;
        else if(vin.length() == 17) return vin.toUpperCase();
        else return null;
    }

    public static String isValidEmail(String email) {
        if(email == null || email.trim().isEmpty()) return null;
        else if(email.length() > 4 && email.length() < 65 && email.matches("-^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$")) return email;
        else return null;
    }

    public static Long isValidLong(String number) {
        if(number == null || number.trim().isEmpty()) return null;
        else if(number.matches("^[0-9]*$")) return Long.parseLong(number);
        else return null;
    }

    public static String isValidPassword(String password) {
        if(password == null || password.trim().isEmpty()) return null;
        else if(password.length() > 8) return password;
        else return null;
    }

    public static String isValidString(String string) {
        if(string == null || string.trim().isEmpty()) return null;
        else return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, string);
    }

    public static Integer isValidYear(String year) {
        if(year == null || year.trim().isEmpty()) return null;
        else if(year.matches("-?[0-9]+") && Integer.parseInt(year) <= LocalDate.now().getYear()) return Integer.parseInt(year);
        else return null;
    }

    public static Object isValidEntity(Object entity) {
        if(entity != null) {
            Field[] entityFields = entity.getClass().getDeclaredFields();
            if(entity instanceof Job) {
                Field[] jobFields = Job.class.getDeclaredFields();
                if(Arrays.equals(entityFields, jobFields)) return entity;
            } else if(entity instanceof Role) {
                Field[] roleFields = Role.class.getDeclaredFields();
                if(Arrays.equals(entityFields, roleFields)) return entity;
            } else if(entity instanceof User) {
                Field[] userFields = User.class.getDeclaredFields();
                if(Arrays.equals(entityFields, userFields)) return entity;
            } else if(entity instanceof Claim) {
                Field[] claimFields = Claim.class.getDeclaredFields();
                if(Arrays.equals(entityFields, claimFields)) return entity;
            } else if(entity instanceof Policy) {
                Field[] policyFields = Policy.class.getDeclaredFields();
                if(Arrays.equals(entityFields, policyFields)) return entity;
            } else if(entity instanceof Vehicle) {
                Field[] vehicleFields = Vehicle.class.getDeclaredFields();
                if (Arrays.equals(entityFields, vehicleFields)) return entity;
            } else if(entity instanceof Company) {
                Field[] companyFields = Company.class.getDeclaredFields();
                if (Arrays.equals(entityFields, companyFields)) return entity;
            }
        }
        return null;
    }

    public static String isValidSubscribingCompany(String name, String type) {
        if(name == null || name.trim().isEmpty() || type == null || type.trim().isEmpty()) return null;
        else if(CompanyType.valueOf(type).level() > 1) return name;
        else return null;
    }
}
