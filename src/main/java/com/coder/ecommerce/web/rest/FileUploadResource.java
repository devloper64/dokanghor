package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.service.FileService;
import com.coder.ecommerce.service.dto.FileDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileUploadResource {

    private final FileService fileService;

    public FileUploadResource(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/upload")
    public FileDTO upload(@RequestParam("file") MultipartFile multipartFile,@RequestParam("directory")String directory) {
        return fileService.upload(multipartFile,directory);
    }
}
