package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models;

import java.util.List;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.enums.DiaSemana;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FranjaHorariaEntity {
    private Long id;
    private DiaSemana dia;
    private String horaInicio;
    private String horaFin;
    private CursoEntity curso;
    private EspacioFisicoEntity espacioFisico;
    private List<DocenteEntity> docentes;
}
