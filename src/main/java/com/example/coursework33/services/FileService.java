package com.example.coursework33.services;

import java.io.File;
import java.nio.file.Path;

/**
 * Сервис для работы с файлами
 */
public interface FileService {

    /**
     * Сохранить в файл
     * @param json - название файла
     * @param path - путь до файла
     */
    void saveToFile(String json, Path path);

    /**
     * Очистить файл
     * @param path - путь до файла
     */
    void cleanFile(Path path);

    /**
     * Получить файл
     * @param filePath - путь до файла
     * @param fileName - название файла
     * @return - файл
     */
    File getFile(String filePath, String fileName);
}
