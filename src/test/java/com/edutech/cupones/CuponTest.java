package com.edutech.cupones;

import com.edutech.cupones.models.Cupon;
import com.edutech.cupones.dto.CuponDTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Esta clase prueba que el Modelo y el DTO se comportan como se espera.
class CuponTest {

    @Test
    @DisplayName("Prueba para verificar la creación de un modelo Cupon")
    void testCrearModeloCupon() {
        // 1. Crear el objeto del modelo
        Cupon cupon = new Cupon();
        cupon.setIdCupon(1);
        cupon.setCodigo("VERANO2025");
        cupon.setDescuento(20);
        cupon.setValidoHasta(LocalDate.of(2025, 2, 28));

        // 2. Validar el modelo
        assertNotNull(cupon);
        assertEquals(1, cupon.getIdCupon());
        assertEquals("VERANO2025", cupon.getCodigo());
        assertEquals(20, cupon.getDescuento());
    }

    @Test
    @DisplayName("Prueba para verificar la creación de un CuponDTO")
    void testCrearCuponDTO() {
        // 1. Crear el objeto DTO
        CuponDTO cuponDTO = new CuponDTO();
        cuponDTO.setIdCupon(2);
        cuponDTO.setCodigo("INVIERNO25");
        cuponDTO.setDescuento(15);
        cuponDTO.setValidoHasta(LocalDate.of(2025, 7, 31));

        // 2. Validar el DTO
        assertNotNull(cuponDTO);
        assertEquals(2, cuponDTO.getIdCupon());
        assertEquals("INVIERNO25", cuponDTO.getCodigo());
        assertEquals(15, cuponDTO.getDescuento());
        assertEquals(LocalDate.of(2025, 7, 31), cuponDTO.getValidoHasta());
    }
}
