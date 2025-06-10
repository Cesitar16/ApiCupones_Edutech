package com.edutech.cupones.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CuponDTO {

    private Integer idCupon;
    private String codigo;
    private Integer descuento;
    private LocalDate validoHasta;
}
