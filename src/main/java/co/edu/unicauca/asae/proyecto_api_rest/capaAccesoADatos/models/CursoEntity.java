package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CursoEntity {
    private Long id;
    private String codigo;
    private String nombre;
    private String grupo;
    private AsignaturaEntity asignatura;
}
