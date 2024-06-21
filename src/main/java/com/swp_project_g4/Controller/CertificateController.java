package com.swp_project_g4.Controller;

import com.swp_project_g4.Model.*;
import com.swp_project_g4.Repository.Repository;
import com.swp_project_g4.Service.Certificate;
import com.swp_project_g4.Service.storage.StorageFileNotFoundException;
import com.swp_project_g4.Service.storage.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/certificate")
public class CertificateController {

    @Autowired
    private Repository repository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private Certificate certificate;

    @RequestMapping(value = "/{learnerID}/{courseID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> lesson(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable int learnerID, @PathVariable int courseID) {
        certificate.createCertificate("certificate.pdf", learnerID, courseID);
        Resource file = storageService.loadAsResource("certificate/certificate.pdf");

        if (file == null)
            return ResponseEntity.notFound().build();

        Learner learner = repository.getLearnerRepository().findById(learnerID).get();

        String certificateFileName = repository.getCourseRepository().findById(courseID).get().getName() + " " +
                learner.getFirstName() + " " + learner.getLastName() + " certificate";
        certificateFileName = certificateFileName.replace(' ', '_');
        certificateFileName += ".pdf";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE,
                "application/pdf; filename=\"" + certificateFileName + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}