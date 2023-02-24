package com.example.coursework33.services.Impl;

import com.example.coursework33.models.operations.OperationType;
import com.example.coursework33.models.operations.SocksOperation;
import com.example.coursework33.models.socks.SocksBatch;
import com.example.coursework33.services.FileOperationService;
import com.example.coursework33.services.SocksOperationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocksOperationServiceImpl implements SocksOperationService {

    private final List<SocksOperation> socksOperationList = new ArrayList<>();

    private final FileOperationService fileOperationService;

    private final ObjectMapper objectMapper;

    public SocksOperationServiceImpl(FileOperationService fileOperationService, ObjectMapper objectMapper) {
        this.fileOperationService = fileOperationService;
        this.objectMapper = objectMapper;
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
            String json = objectMapper.writeValueAsString(socksOperationList);
            fileOperationService.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
