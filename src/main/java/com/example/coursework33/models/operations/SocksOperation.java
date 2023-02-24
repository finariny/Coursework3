package com.example.coursework33.models.operations;

import com.example.coursework33.models.socks.SocksBatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocksOperation {
    private OperationType operationType;
    private final LocalDateTime localDateTime = LocalDateTime.now();
    private SocksBatch socksBatch;
}
