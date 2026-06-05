package sistemadegestion.demo.controller;

import sistemadegestion.demo.service.S3Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public String subirArchivo(
            @RequestParam("file") MultipartFile file) {

        try {
            return s3Service.subirArchivo(file);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}