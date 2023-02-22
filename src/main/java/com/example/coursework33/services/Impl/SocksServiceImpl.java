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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocksServiceImpl implements SocksService {

    @Value("${path.to.socks.file}")
    private String socksFilePath;

    @Value("${name.of.socks.file}")
    private String socksFileName;

    private final Map<Socks, Integer> socksMap = new HashMap<>();
    private final ValidationService validationService;
    private final FileService fileService;

    private final SocksOperationService socksOperationService;

    public SocksServiceImpl(ValidationService validationService,
                            FileService fileService,
                            SocksOperationService socksOperationService) {
        this.validationService = validationService;
        this.fileService = fileService;
        this.socksOperationService = socksOperationService;
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
                socksOperationService.issuance(socksBatch);
                return socks;
            }
        }
        return null;
    }

    @Override
    public Socks reject(SocksBatch socksBatch) {
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
                socksOperationService.reject(socksBatch);
                return socks;
            }
        }
        return null;
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
            String json = new ObjectMapper().writeValueAsString(socksMap);
            fileService.saveToFile(json, Path.of(socksFilePath, socksFileName));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
