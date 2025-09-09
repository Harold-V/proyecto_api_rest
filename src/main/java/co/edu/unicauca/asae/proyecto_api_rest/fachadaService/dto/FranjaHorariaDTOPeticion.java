package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.dto;

import java.util.List;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.enums.DiaSemana;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para crear/actualizar franjas.
 * Horas en formato "HH:mm" 24h (ej: "08:00", "14:30").
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FranjaHorariaDTOPeticion {
    private DiaSemana dia;
    private String horaInicio; // "HH:mm"
    private String horaFin; // "HH:mm"
    private Long cursoId;
    private Long espacioFisicoId;
    private List<Long> docentesIds;
    private String observaciones;
}
