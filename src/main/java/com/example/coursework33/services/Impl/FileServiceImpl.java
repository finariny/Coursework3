package com.example.coursework33.services.Impl;

import com.example.coursework33.services.FileService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public void saveToFile(String json, Path path) {
        try {
            cleanFile(path);
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanFile(Path path) {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFile(String filePath, String fileName) {
        return new File(filePath + "/" + fileName);
    }
}
