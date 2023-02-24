package com.example.coursework33.services;

import java.io.File;

/**
 * Сервис для работы с файлами операций
 */
public interface FileOperationService {

    /**
     * Сохранить в файл операций
     * @param json - название файла
     */
    void saveToFile(String json);

    /**
     * Очистить файл операций
     */
    void cleanFile();

    /**
     * Получить файл операций
     * @return - файл
     */
    File getFile();

    /**
     * Очистить и получить файл операций
     * @return - файл
     */
    File cleanAndGetFile();
}
