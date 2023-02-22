package com.example.coursework33.controllers;

import com.example.coursework33.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/socks/files")
@Tag(
        name = "Файлы",
        description = "Импорт и экспорт файлов со списком носков"
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "500",
                description = "Произошла ошибка, не зависящая от вызывающей стороны"
        )
})
public class FileController {

    @Value("${path.to.socks.file}")
    private String socksFilePath;

    @Value("${name.of.socks.file}")
    private String socksFileName;

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Скачать список всех носков в виде json-файла"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл доступен для скачивания"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Файл пуст"
            )
    })
    public ResponseEntity<InputStreamResource> downloadFile() throws FileNotFoundException {
        File file = fileService.getFile(socksFilePath, socksFileName);
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SocksLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузить json-файл со списком носков"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл загружен"
            )
    })
    public ResponseEntity<Void> uploadFile(@RequestParam MultipartFile multipartFile) {
        fileService.cleanFile(Path.of(socksFilePath, socksFileName));
        File file = fileService.getFile(socksFilePath, socksFileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            IOUtils.copy(multipartFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}