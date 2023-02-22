package com.example.coursework33.services;

import com.example.coursework33.models.socks.SocksBatch;

/**
 * Сервис для работы с операциями над носками
 */
public interface SocksOperationService {

    /**
     * Регистрирация прихода носков на склад
     * @param socksBatch - партия носков (носки + количество)
     */
    void accept(SocksBatch socksBatch);

    /**
     * Регистрирация отпуска носков со склада
     * @param socksBatch - партия носков (носки + количество)
     */
    void issuance(SocksBatch socksBatch);

    /**
     * Регистрирация списания испорченных (бракованных) носков
     * @param socksBatch - партия носков (носки + количество)
     */
    void reject(SocksBatch socksBatch);
}
