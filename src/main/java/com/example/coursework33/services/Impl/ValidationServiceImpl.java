package com.example.coursework33.services.Impl;

import com.example.coursework33.models.socks.Color;
import com.example.coursework33.models.socks.Size;
import com.example.coursework33.models.socks.SocksBatch;
import com.example.coursework33.services.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validateSocks(SocksBatch socksBatch) {
        return socksBatch != null
                && socksBatch.getSocks().getColor() != null
                && socksBatch.getSocks().getSize() != null
                && socksBatch.getSocks().getCottonPart() >= 0
                && socksBatch.getQuantity() > 0;
    }

    @Override
    public boolean validateSocks(Color color, Size size, int cottonMin, int cottonMax) {
        return color != null
                && size != null
                && cottonMin >= 0
                && cottonMax >= 0
                && checkCotton(cottonMin, cottonMax);
    }

    private boolean checkCotton(int cottonMin, int cottonMax) {
        return cottonMin >= 0 && cottonMin <= 100 && cottonMax >= 0 && cottonMax <= 100;
    }
}