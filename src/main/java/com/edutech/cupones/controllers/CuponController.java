package com.edutech.cupones.controllers;

import com.edutech.cupones.dto.CuponDTO;
import com.edutech.cupones.services.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cupones")
public class CuponController {

    @Autowired
    private CuponService service;

    @PostMapping
    public ResponseEntity<CuponDTO> crear(@RequestBody CuponDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<CuponDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuponDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuponDTO> actualizar(@PathVariable Integer id, @RequestBody CuponDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}