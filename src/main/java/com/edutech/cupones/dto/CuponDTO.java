package com.edutech.cupones.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@Data
public class CuponDTO extends RepresentationModel <CuponDTO>{

    private Integer idCupon;
    private String codigo;
    private Integer descuento;
    private LocalDate validoHasta;
}
