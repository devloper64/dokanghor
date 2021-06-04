package com.coder.ecommerce.service;

import com.coder.ecommerce.service.dto.FileDTO;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class FileService {



    public FileDTO upload(MultipartFile multipartFile) {

        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            assert fileName != null;
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            return new FileDTO("Successfully Uploaded !", URL.replace("product/","product%2F"));                     // Your customized response
        } catch (Exception e) {
            e.printStackTrace();
            return  new FileDTO( "Unsuccessfully Uploaded!","Not found!");
        }

    }

    private String uploadFile(File file, String fileName) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", fileName);
        BlobId blobId = BlobId.of("ecommerce-d6878.appspot.com", "product/"+fileName);
        URL resource = ClassLoader.getSystemResource("ecommerce-d6878-firebase-adminsdk-12jam-45806abdd1.jso" +
            "n");
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setMetadata(map).setContentType("media").build();
        assert resource != null;
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(resource.getFile()));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format("https://firebasestorage.googleapis.com/v0/b/ecommerce-d6878.appspot.com/o/product/%s?alt=media&token="+fileName, URLEncoder.encode(fileName, String.valueOf(StandardCharsets.UTF_8)));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
