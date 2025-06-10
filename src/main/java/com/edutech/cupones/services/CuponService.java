package com.edutech.cupones.services;

import com.edutech.cupones.dto.CuponDTO;
import com.edutech.cupones.models.Cupon;
import com.edutech.cupones.repository.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuponService {

    @Autowired
    private CuponRepository repository;

    // Para Guardar los cupones nuevos
    public CuponDTO guardar(CuponDTO dto) {
        Cupon cupon = toEntity(dto);
        Cupon saved = repository.save(cupon);
        return toDTO(saved);
    }

    // Para obtener una Lista de CuponDTO con todos los cupones Existentes
    public List<CuponDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar cupón por ID
    public Optional<CuponDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    // Actualiza los datos de un cupón Existente
    public Optional<CuponDTO> actualizar(Integer id, CuponDTO dto) {
        return repository.findById(id)
                .map(cupon -> {
                    cupon.setCodigo(dto.getCodigo());
                    cupon.setDescuento(dto.getDescuento());
                    cupon.setValidoHasta(dto.getValidoHasta());
                    Cupon actualizado = repository.save(cupon);
                    return toDTO(actualizado);
                });
    }

    // Para Eliminar un cupón de la base de datos
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Métodos auxiliares---

    // Convierte una entidad Cupon a un DTO
    private CuponDTO toDTO(Cupon cupon) {
        CuponDTO dto = new CuponDTO();
        dto.setIdCupon(cupon.getIdCupon());
        dto.setCodigo(cupon.getCodigo());
        dto.setDescuento(cupon.getDescuento());
        dto.setValidoHasta(cupon.getValidoHasta());
        return dto;
    }

    // Convierte un DTO a una entidad Cupon
    private Cupon toEntity(CuponDTO dto) {
        Cupon cupon = new Cupon();
        cupon.setIdCupon(dto.getIdCupon());
        cupon.setCodigo(dto.getCodigo());
        cupon.setDescuento(dto.getDescuento());
        cupon.setValidoHasta(dto.getValidoHasta());
        return cupon;
    }
}