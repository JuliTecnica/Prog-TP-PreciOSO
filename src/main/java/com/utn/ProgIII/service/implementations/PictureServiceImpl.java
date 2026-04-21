package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.exceptions.InternalServerError;
import com.utn.ProgIII.service.interfaces.PictureService;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

@Service
public class PictureServiceImpl implements PictureService {

    @Override
    public String uploadPicture(String path, MultipartFile file) {
        String extension = "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date()) + extension;



        String filepath = path + File.separator + filename;

        System.out.println(filepath);
        File f = new File(path);
        if (!f.exists())
        {
            boolean created = f.mkdirs();
        }

        try {
            Files.copy(file.getInputStream(), Paths.get(filepath));
        } catch (IOException e) {
            throw new InternalServerError("Error cargando archivo!");
        }

        return filename;
    }

    @Override
    public InputStream getResourceFile(String path, String filename) {
        String fullpath = path + File.separator + filename;

        InputStream file;
        try {
            file = new FileInputStream(fullpath);
        } catch (FileNotFoundException e)
        {
            file = null;
        }

        return file;
    }
}
