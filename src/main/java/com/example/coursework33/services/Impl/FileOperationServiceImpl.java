package com.example.coursework33.services.Impl;

import com.example.coursework33.services.FileOperationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileOperationServiceImpl implements FileOperationService {

    @Value("${path.to.socks.operations.file}")
    private String socksOperationsFilePath;

    @Value("${name.of.socks.operations.file}")
    private String socksOperationsFileName;

    @Override
    public void saveToFile(String json) {
        try {
            Path path = Path.of(socksOperationsFilePath, socksOperationsFileName);
            cleanFile();
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanFile() {
        try {
            Path path = Path.of(socksOperationsFilePath, socksOperationsFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFile() {
        return new File(socksOperationsFilePath + "/" + socksOperationsFileName);
    }

    @Override
    public File cleanAndGetFile() {
        cleanFile();
        return getFile();
    }
}
