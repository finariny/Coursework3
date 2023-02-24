package com.example.coursework33.services;

import java.io.File;

/**
 * Сервис для работы с файлами
 */
public interface FileService {

    /**
     * Сохранить в файл
     * @param json - название файла
     */
    void saveToFile(String json);

    /**
     * Очистить файл
     */
    void cleanFile();

    /**
     * Получить файл
     * @return - файл
     */
    File getFile();

    /**
     * Очистить и получить файл
     * @return - файл
     */
    File cleanAndGetFile();
}
