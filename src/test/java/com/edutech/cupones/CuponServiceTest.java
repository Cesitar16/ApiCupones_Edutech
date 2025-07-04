package com.edutech.cupones;

import com.edutech.cupones.dto.CuponDTO;
import com.edutech.cupones.models.Cupon;
import com.edutech.cupones.repository.CuponRepository;
import com.edutech.cupones.services.CuponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


// Import estático para usar assertThat() de AssertJ
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuponServiceTest {

    @Mock
    private CuponRepository cuponRepository;

    @InjectMocks
    private CuponService cuponService;


    // Se crea un 'mock' para la dependencia del servicio.
    @Mock
    private CuponRepository cuponRepository;

    // Se inyecta el mock en la instancia del servicio a probar.
    @InjectMocks
    private CuponService cuponService;

    // Objetos de prueba reutilizables.
    private Cupon cupon;
    private CuponDTO cuponDTO;

    @BeforeEach
    void setUp() {

        // Se configuran los datos de prueba antes de cada test.

        cuponDTO = new CuponDTO();
        cuponDTO.setIdCupon(1);
        cuponDTO.setCodigo("DESCUENTO25");
        cuponDTO.setDescuento(25);
        cuponDTO.setValidoHasta(LocalDate.of(2025, 12, 31));

        cupon = new Cupon();
        cupon.setIdCupon(1);
        cupon.setCodigo("DESCUENTO25");
        cupon.setDescuento(25);
        cupon.setValidoHasta(LocalDate.of(2025, 12, 31));
    }

    @Test
    @DisplayName("Prueba del método guardar()")
    void testGuardar() {

        // Arrange
        when(cuponRepository.save(any(Cupon.class))).thenReturn(cupon);

        // Act
        CuponDTO resultado = cuponService.guardar(cuponDTO);

        // Assert: Ahora usando AssertJ
        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdCupon()).isEqualTo(1);
        assertThat(resultado.getCodigo()).isEqualTo("DESCUENTO25");

        // Arrange: Se le dice a Mockito qué devolver cuando se llame a save().
        when(cuponRepository.save(any(Cupon.class))).thenReturn(cupon);

        CuponDTO resultado = cuponService.guardar(cuponDTO);

        // Assert: Se verifica que el resultado es correcto.
        assertNotNull(resultado);
        assertEquals("DESCUENTO25", resultado.getCodigo());
        assertEquals(1, resultado.getIdCupon());

        verify(cuponRepository, times(1)).save(any(Cupon.class));
    }

    @Test
    @DisplayName("Prueba del método listar()")
    void testListar() {
        // Arrange

        // Arrange: Se simula que el repositorio devuelve una lista con un cupón.

        when(cuponRepository.findAll()).thenReturn(List.of(cupon));

        // Act
        List<CuponDTO> resultados = cuponService.listar();

        // Assert

        assertThat(resultados).isNotEmpty();
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getDescuento()).isEqualTo(25);

        assertFalse(resultados.isEmpty());
        assertEquals(1, resultados.size());
        assertEquals(25, resultados.get(0).getDescuento());

    }

    @Test
    @DisplayName("Prueba del método obtenerPorId() cuando el cupón existe")
    void testObtenerPorIdEncontrado() {
        // Arrange
        when(cuponRepository.findById(1)).thenReturn(Optional.of(cupon));

        // Act
        Optional<CuponDTO> resultado = cuponService.obtenerPorId(1);

        // Assert

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getIdCupon()).isEqualTo(1);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getIdCupon());

    }

    @Test
    @DisplayName("Prueba del método obtenerPorId() cuando el cupón no existe")
    void testObtenerPorIdNoEncontrado() {
        // Arrange
        // Arrange: Se simula que el repositorio devuelve un Optional vacío.

        when(cuponRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<CuponDTO> resultado = cuponService.obtenerPorId(99);

        // Assert
        assertThat(resultado).isEmpty();
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Prueba del método actualizar()")
    void testActualizar() {
        // Arrange
        CuponDTO dtoActualizado = new CuponDTO();
        dtoActualizado.setCodigo("NUEVOCODIGO");
        dtoActualizado.setDescuento(50);

        when(cuponRepository.findById(1)).thenReturn(Optional.of(cupon));
        when(cuponRepository.save(any(Cupon.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<CuponDTO> resultado = cuponService.actualizar(1, dtoActualizado);

        // Assert

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCodigo()).isEqualTo("NUEVOCODIGO");
        assertThat(resultado.get().getDescuento()).isEqualTo(50);

        assertTrue(resultado.isPresent());
        assertEquals("NUEVOCODIGO", resultado.get().getCodigo());
        assertEquals(50, resultado.get().getDescuento());
        verify(cuponRepository, times(1)).save(any(Cupon.class));
    }

    @Test
    @DisplayName("Prueba del método actualizar() cuando el cupón no existe")
    void testActualizarNoExistente() {
        // Arrange
        when(cuponRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<CuponDTO> resultado = cuponService.actualizar(99, cuponDTO);

        // Assert
        assertThat(resultado).isEmpty();
        verify(cuponRepository, never()).save(any(Cupon.class));
    }

        assertTrue(resultado.isEmpty());
        verify(cuponRepository, never()).save(any(Cupon.class));
    }


    @Test
    @DisplayName("Prueba del método eliminar() cuando el cupón existe")
    void testEliminar() {
        // Arrange
        when(cuponRepository.existsById(1)).thenReturn(true);
        doNothing().when(cuponRepository).deleteById(1);

        // Act
        boolean resultado = cuponService.eliminar(1);

        // Assert
        assertThat(resultado).isTrue();


        assertTrue(resultado);

        verify(cuponRepository, times(1)).existsById(1);
        verify(cuponRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Prueba del método eliminar() cuando el cupón no existe")
    void testEliminarNoExistente() {
        // Arrange
        when(cuponRepository.existsById(99)).thenReturn(false);

        // Act
        boolean resultado = cuponService.eliminar(99);

        // Assert
        assertThat(resultado).isFalse();

        verify(cuponRepository, times(1)).existsById(99);
        verify(cuponRepository, never()).deleteById(99);

        assertFalse(resultado);
        verify(cuponRepository, times(1)).existsById(99);
        verify(cuponRepository, never()).deleteById(99); // Se verifica que NUNCA se llamó a delete.

    }
}