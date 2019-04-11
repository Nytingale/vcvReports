package com.vcv.backend.services;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.FileServiceException;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.configs.FileStorageConfig;
import com.vcv.backend.views.MessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Optional;

@Service
public class FileService {

    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private FileStorageConfig fileStorageConfig;

    public MessageView.FileUpload uploadImage(User admin,
                                              MultipartFile image,
                                              HttpServletRequest request) throws FileServiceException {
        // First, Confirm that the Admin is an Admin or VCV Staff
        if(admin.getRoleId() > 1) {
            throw new FileServiceException("Error 605: uploadImage(admin, image, request) has failed to identify the Admin as a Company Admin or VCV Staff");
        }

        // Second, Find the Admin's Company
        Optional<Company> company = companyRepository.findById(admin.getCompanyId());
        if(company.isEmpty()) throw new FileServiceException("Error 610: uploadImage(admin, image, request) could not find the Admin's Company");

        // Third, Create the Server-Side File Location and Name based on the Company's Name
        File targetImage = new File(request.getServletContext().getRealPath(fileStorageConfig.getImagesDir()), company.get().getCompanyName());

        try {
            // Fourth, Upload the Image to the Server
            image.transferTo(targetImage);
            return new MessageView.FileUpload().build(image, company.get().getCompanyName(), "Successfully Uploaded Image");
        } catch(Exception e) {
            throw new FileServiceException("Error 615: uploadImage(admin, image, request) has failed to Upload the Image");
        }
    }
}
