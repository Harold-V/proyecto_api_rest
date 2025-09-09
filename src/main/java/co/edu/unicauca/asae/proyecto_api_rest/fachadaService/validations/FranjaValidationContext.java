package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations;

import java.util.List;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.enums.DiaSemana;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.CursoEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.DocenteEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.EspacioFisicoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FranjaValidationContext {
    private final Long excluirFranjaId; // null en crear; id en actualizar
    private final DiaSemana dia;
    private final String horaInicio; // "HH:mm"
    private final String horaFin; // "HH:mm"
    private final CursoEntity curso;
    private final EspacioFisicoEntity espacio;
    private final List<DocenteEntity> docentes;
}
