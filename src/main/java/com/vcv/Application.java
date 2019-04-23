package com.vcv;

import com.vcv.backend.configs.SecurityConfig;
import com.vcv.backend.services.*;
import com.vcv.frontend.MainLayout;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(
        scanBasePackageClasses = {
                MainLayout.class,

                JobService.class,
                FileService.class,
                UserService.class,
                ClaimService.class,
                PolicyService.class,
                CompanyService.class,
                VehicleService.class,

                Application.class,

                SecurityConfig.class},
        exclude = ErrorMvcAutoConfiguration.class)
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}

