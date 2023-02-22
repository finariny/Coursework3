package com.example.coursework33.services.Impl;

import com.example.coursework33.models.operations.OperationType;
import com.example.coursework33.models.operations.SocksOperation;
import com.example.coursework33.models.socks.SocksBatch;
import com.example.coursework33.services.FileService;
import com.example.coursework33.services.SocksOperationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocksOperationServiceImpl implements SocksOperationService {

    @Value("${path.to.socks.operations.file}")
    private String socksOperationsFilePath;

    @Value("${name.of.socks.operations.file}")
    private String socksOperationsFileName;

    private final List<SocksOperation> socksOperationList = new ArrayList<>();
    private final FileService fileService;

    public SocksOperationServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void accept(SocksBatch socksBatch) {
        socksOperationList.add(new SocksOperation(OperationType.ACCEPT, socksBatch));
        saveToFile();
    }

    @Override
    public void issuance(SocksBatch socksBatch) {
        socksOperationList.add(new SocksOperation(OperationType.ISSUANCE, socksBatch));
        saveToFile();
    }

    @Override
    public void reject(SocksBatch socksBatch) {
        socksOperationList.add(new SocksOperation(OperationType.REJECT, socksBatch));
        saveToFile();
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksOperationList);
            fileService.saveToFile(json, Path.of(socksOperationsFilePath, socksOperationsFileName));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
