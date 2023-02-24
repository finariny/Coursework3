package com.example.coursework33.services.Impl;

import com.example.coursework33.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    @Value("${path.to.socks.file}")
    private String socksFilePath;

    @Value("${name.of.socks.file}")
    private String socksFileName;

    @Override
    public void saveToFile(String json) {
        try {
            Path path = Path.of(socksFilePath, socksFileName);
            cleanFile();
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanFile() {
        try {
            Path path = Path.of(socksFilePath, socksFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFile() {
        return new File(socksFilePath + "/" + socksFileName);
    }

    @Override
    public File cleanAndGetFile() {
        cleanFile();
        return getFile();
    }
}
