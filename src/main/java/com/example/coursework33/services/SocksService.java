package com.example.coursework33.services;

import com.example.coursework33.models.socks.Color;
import com.example.coursework33.models.socks.Size;
import com.example.coursework33.models.socks.Socks;
import com.example.coursework33.models.socks.SocksBatch;

/**
 * Сервис для работы с носками
 */
public interface SocksService {

    /**
     * Добавление новых носков
     * @param socksBatch - партия носков (носки + количество)
     * @return - носки
     */
    Socks accept(SocksBatch socksBatch);

    /**
     * Отпуск носков со склада
     * @param socksBatch - партия носков (носки + количество)
     * @return - носки
     */
    Socks issuance(SocksBatch socksBatch);

    /**
     * Списание испорченных (бракованных) носков со склада
     * @param socksBatch - партия носков (носки + количество)
     * @return - носки
     */
    Socks reject(SocksBatch socksBatch);

    /**
     * Получение данных о носках, соответствующих параметрам
     * @param color - цвет
     * @param size - размер
     * @param cottonMin - минимальное процентное содержание хлопка
     * @param cottonMax - максимальное процентное содержание хлопка
     * @return - количество носков
     */
    Integer getNumberOfSocks(Color color, Size size, int cottonMin, int cottonMax);
}
