package com.example.imageSaver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SearchImageService {

    @Autowired
    public  UploadImageService uploadImageService;

    Path originalStorageDirectory= Paths.get("./uploads/originals/");
    Path thumbnailStorageDirectory=Paths.get("./uploaded/thumbnails/");

    public Resource searchAndLoadFileResource(String imageTitle) throws FileNotFoundException {
        try {
            // Resolve the complete file path safely
            Path filePath = this.thumbnailStorageDirectory.resolve(imageTitle).normalize().toAbsolutePath();
            Path normalizedPath = this.thumbnailStorageDirectory.normalize().toAbsolutePath();
            if(!filePath.startsWith(normalizedPath)){
                throw  new SecurityException("access denied");
            }


            // Check if the file exists and is readable
            if (Files.exists(filePath) && Files.isReadable(filePath)) {
//                return new UrlResource(filePath.toUri());

                return new UrlResource(uploadImageService.getImageThumbnailResource(imageTitle).getURL());
            } else {
                throw new FileNotFoundException("File not found: " + imageTitle);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File path calculation failed for: " + imageTitle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
