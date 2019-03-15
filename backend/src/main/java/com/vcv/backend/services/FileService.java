package com.vcv.backend.services;

import com.vcv.backend.configs.FileStorageConfig;
import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.FileServiceException;
import com.vcv.backend.exceptions.UserServiceException;
import com.vcv.backend.repositories.UserRepository;
import com.vcv.backend.views.MessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Service
public class FileService {

    @Autowired private UserRepository userRepository;
    @Autowired private FileStorageConfig fileStorageConfig;

    public MessageView.FileUpload uploadImage(User admin,
                                              MultipartFile image,
                                              HttpServletRequest request) throws FileServiceException {
        // First, Confirm that the User is an Admin or VCV Staff
        if(!admin.isAdmin() && admin.getCompanyType().level() != 3) {
            throw new FileServiceException("Error 605: uploadImage(admin, image, request) has failed to identify the User as a Company Admin or VCV Staff");
        }

        // Second, Create the Server-Side File Location and Name based on the Company's Name
        File targetImage = new File(request.getServletContext().getRealPath(fileStorageConfig.getImagesDir()), admin.getCompanyName());

        try {
            // Third, Upload the Image to the Server
            image.transferTo(targetImage);
            return new MessageView.FileUpload().build(image, admin.getCompanyName(), "Successfully Uploaded Image");
        } catch(Exception e) {
            throw new FileServiceException("Error 610: uploadImage(admin, image, request) has failed to Upload the Image");
        }
    }
}
