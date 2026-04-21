package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.service.interfaces.PictureService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PictureServiceImpl implements PictureService {

    @Override
    public String uploadPicture(String path, MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();

        String filepath = path + File.separator + filename;

        File f = new File(filepath);

        if (!f.exists())
        {
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filepath));

        return filename;
    }

    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
        String fullpath = path + File.separator + filename;
        return new FileInputStream(fullpath);
    }
}
