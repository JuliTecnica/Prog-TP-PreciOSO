package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.exceptions.BadRequestException;
import com.utn.ProgIII.exceptions.InternalServerError;
import com.utn.ProgIII.exceptions.NotFoundException;
import com.utn.ProgIII.service.interfaces.PictureService;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final List<String> ALLOWED_FILE_EXTENSIONS = Arrays.asList(".jpg", ".png", ".webp", ".jpeg");
    private final List<String> ALLOWED_FILE_TYPES = Arrays.asList("image/jpeg","image/png");

    @Override
    public String uploadPicture(String path, MultipartFile file) {
        String extension = "." + StringUtils.getFilenameExtension(file.getOriginalFilename());

        if(!ALLOWED_FILE_TYPES.contains(file.getContentType()))
        {
            throw new BadRequestException("Los tipos de archivos permitidos son " + String.join(", ", ALLOWED_FILE_EXTENSIONS.stream().map(String::valueOf).toList()));
        }

        if(!ALLOWED_FILE_EXTENSIONS.contains(extension))
        {
            throw new BadRequestException("Los tipos de archivos permitidos son " + String.join(", ", ALLOWED_FILE_EXTENSIONS.stream().map(String::valueOf).toList()));
        }

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
            throw new NotFoundException("Archivo no encontrado!");
        }

        return file;
    }
}
