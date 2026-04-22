package com.utn.ProgIII.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface PictureService {
    String uploadPicture(String path, MultipartFile file);
    InputStream getResourceFile(String path, String filename);
    boolean deleteFile(String path, String filename);
}
