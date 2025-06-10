package com.edutech.cupones.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cupon")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Cupon {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental (MySQL)
    @Column(name = "id_cupon")
    private Integer idCupon;

    @Column(name = "codigo", nullable = false, length = 10)
    private String codigo;

    @Column(name = "descuento", nullable = false)
    private Integer descuento;

    @Column(name = "valido_hasta", nullable = false)
    private LocalDate validoHasta;
    
}
