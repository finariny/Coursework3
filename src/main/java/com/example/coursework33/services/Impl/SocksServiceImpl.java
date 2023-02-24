package com.example.coursework33.services.Impl;

import com.example.coursework33.models.socks.Color;
import com.example.coursework33.models.socks.Size;
import com.example.coursework33.models.socks.Socks;
import com.example.coursework33.models.socks.SocksBatch;
import com.example.coursework33.services.FileService;
import com.example.coursework33.services.SocksOperationService;
import com.example.coursework33.services.SocksService;
import com.example.coursework33.services.ValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SocksServiceImpl implements SocksService {

    private final Map<Socks, Integer> socksMap = new HashMap<>();
    private final ValidationService validationService;
    private final FileService fileService;

    private final SocksOperationService socksOperationService;

    private final ObjectMapper objectMapper;

    public SocksServiceImpl(ValidationService validationService,
                            FileService fileService,
                            SocksOperationService socksOperationService, ObjectMapper objectMapper) {
        this.validationService = validationService;
        this.fileService = fileService;
        this.socksOperationService = socksOperationService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Socks accept(SocksBatch socksBatch) {
        if (validationService.validateSocks(socksBatch)) {
            Socks socks = socksBatch.getSocks();
            if (socksMap.containsKey(socks)) {
                int currentQuantity = socksMap.get(socks);
                socksMap.replace(socks, currentQuantity + socksBatch.getQuantity());
            } else {
                socksMap.put(socks, socksBatch.getQuantity());
            }
            saveToFile();
            socksOperationService.accept(socksBatch);
            return socks;
        }
        return null;
    }

    @Override
    public Socks issuance(SocksBatch socksBatch) {
        Socks socks = issuanceAndReject(socksBatch);
        if (socks != null) {
            socksOperationService.issuance(socksBatch);
        }
        return socks;
    }

    @Override
    public Socks reject(SocksBatch socksBatch) {
        Socks socks = issuanceAndReject(socksBatch);
        if (socks != null) {
            socksOperationService.reject(socksBatch);
        }
        return socks;
    }

    @Override
    public Integer getNumberOfSocks(Color color, Size size, int cottonMin, int cottonMax) {
        if (validationService.validateSocks(color, size, cottonMin, cottonMax)) {
            int socksCounter = 0;
            for (Map.Entry<Socks, Integer> entry : socksMap.entrySet()) {
                if ((entry.getKey().getColor() == color
                        && entry.getKey().getSize() == size
                        && entry.getKey().getCottonPart() >= cottonMin
                        && entry.getKey().getCottonPart() <= cottonMax)) {
                    socksCounter = socksCounter + entry.getValue();
                }
            }
            return socksCounter;
        }
        return null;
    }

    private void saveToFile() {
        try {
            String json = objectMapper.writeValueAsString(socksMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Socks issuanceAndReject(SocksBatch socksBatch) {
        if (validationService.validateSocks(socksBatch)) {
            Socks socks = socksBatch.getSocks();
            if (socksMap.containsKey(socks)) {
                int currentQuantity = socksMap.get(socks);
                if (currentQuantity > socksBatch.getQuantity()) {
                    socksMap.replace(socks, currentQuantity - socksBatch.getQuantity());
                } else {
                    socksMap.remove(socks);
                }
                saveToFile();
                return socks;
            }
        }
        return null;
    }
}
