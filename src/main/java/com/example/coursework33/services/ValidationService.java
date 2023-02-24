package com.example.coursework33.services;

import com.example.coursework33.models.socks.Color;
import com.example.coursework33.models.socks.Size;
import com.example.coursework33.models.socks.SocksBatch;

/**
 * Сервис валидации
 */
public interface ValidationService {

    /**
     * Валидация носков
     * @param socksBatch - партия носков (носки + количество)
     * @return - true (носки корректны) / false (носки не корректны)
     */
    boolean validateSocks(SocksBatch socksBatch);

    /**
     * Валидация носков по параметрам
     * @param color - цвет
     * @param size - размер
     * @param cottonMin - минимальное процентное содержание хлопка
     * @param cottonMax - максимальное процентное содержание хлопка
     * @return - true (носки корректны) / false (носки не корректны)
     */
    boolean validateSocks(Color color, Size size, int cottonMin, int cottonMax);
}
