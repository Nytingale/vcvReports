package com.vcv.backend.utilities;

import java.time.LocalDate;

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

    public static Integer isValidYear(String year) {
        if(year == null || year.trim().isEmpty()) return null;
        else if(year.matches("-?[0-9]+") && Integer.parseInt(year) > LocalDate.now().getYear()) return Integer.parseInt(year);
        else return null;
    }
}
