package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.dto;

import java.util.List;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.enums.DiaSemana;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FranjaHorariaDTO {
    private DiaSemana dia;
    private String horaInicio; // "HH:mm"
    private String horaFin; // "HH:mm"
    private Long cursoId;
    private Long espacioFisicoId;
    private List<Long> docentesIds;
    private String observaciones;
}
