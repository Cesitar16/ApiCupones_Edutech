package com.edutech.cupones.controllers;

import com.edutech.cupones.dto.CuponDTO;
import com.edutech.cupones.services.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cupones")
public class CuponController {

    @Autowired
    private CuponService service;

    // --- Métodos CRUD estándar (sin cambios) ---
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

    // ======================================================
    // MÉTODOS HATEOAS
    // ======================================================

    /**
     * Obtiene un cupón por su ID y le añade enlaces HATEOAS.
     */
    @GetMapping("/hateoas/{id}")
    public CuponDTO obtenerHATEOAS(@PathVariable Integer id) {
        CuponDTO dto = service.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Cupón no encontrado con id: " + id));

        // URL base del Gateway para los cupones
        String gatewayUrl = "http://localhost:8888/api/proxy/cupones";

        // --- Enlaces apuntando exclusivamente al API Gateway ---

        // Link a sí mismo
        dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());
        
        // Link a la lista de todos los cupones
        dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todos-los-cupones"));
        
        // Link para eliminar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));
        
        // Link para actualizar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));

        return dto;
    }

    /**
     * Obtiene todos los cupones y añade enlaces HATEOAS a cada uno.
     */
    @GetMapping("/hateoas")
    public List<CuponDTO> listarHATEOAS() {
        List<CuponDTO> cupones = service.listar();
        String gatewayUrl = "http://localhost:8888/api/proxy/cupones";

        for (CuponDTO dto : cupones) {
            // Link a los detalles de este cupón (usando el ID del DTO)
            // Asegúrate de que tu CuponDTO tenga un método para obtener el ID, como getIdCupon()
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdCupon()).withSelfRel());

            // Link para crear un nuevo cupón
            dto.add(Link.of(gatewayUrl).withRel("crear-nuevo-cupon").withType("POST"));
        }

        return cupones;
    } 
}
