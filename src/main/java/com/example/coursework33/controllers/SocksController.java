package com.example.coursework33.controllers;

import com.example.coursework33.models.socks.Color;
import com.example.coursework33.models.socks.Size;
import com.example.coursework33.models.socks.Socks;
import com.example.coursework33.models.socks.SocksBatch;
import com.example.coursework33.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(
        name = "Веб-приложение для склада интернет-магазина носков",
        description = "CRUD-операции для работы с носками"
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "500",
                description = "Произошла ошибка, не зависящая от вызывающей стороны"
        )
})
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService sockService) {
        this.socksService = sockService;
    }

    @PostMapping
    @Operation(
            summary = "Регистрация прихода товара на склад"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось добавить приход"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            )
    })
    public ResponseEntity<Socks> acceptSocks(@RequestBody SocksBatch socksBatch) {
        Socks addedSock = socksService.accept(socksBatch);
        if (addedSock == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(addedSock);
    }

    @PutMapping
    @Operation(
            summary = "Регистрация отпуска носков со склада."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось произвести отпуск носков со склада"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"
            )
    })
    public ResponseEntity<Socks> issuanceSocks(@RequestBody SocksBatch socksBatch) {
        Socks removedSock = socksService.issuance(socksBatch);
        if (removedSock == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(
            summary = "Получение общего количества носков на складе, соответствующих переданным в параметрах критериям запроса"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен, результат в теле ответа в виде строкового представления целого числа"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            )
    })
    public ResponseEntity<Integer> getNumberOfSocks(@RequestParam Color color,
                                                    @RequestParam Size size,
                                                    @RequestParam int cottonMin,
                                                    @RequestParam int cottonMax) {
        Integer socksCount = socksService.getNumberOfSocks(color, size, cottonMin, cottonMax);
        if (socksCount == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(socksCount);
    }

    @DeleteMapping
    @Operation(
            summary = "Регистрация списания испорченных (бракованных) носков"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен, товар списан со склада"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            )
    })
    public ResponseEntity<Socks> rejectSocks(@RequestBody SocksBatch socksBatch) {
        Socks removedSock = socksService.reject(socksBatch);
        if (removedSock == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
