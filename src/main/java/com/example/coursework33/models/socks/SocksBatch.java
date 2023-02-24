package com.example.coursework33.models.socks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocksBatch {
    private Socks socks;
    private int quantity;
}