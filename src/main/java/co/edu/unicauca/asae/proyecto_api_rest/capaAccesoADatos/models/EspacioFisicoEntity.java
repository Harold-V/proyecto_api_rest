package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.enums.TipoEspacioFisico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EspacioFisicoEntity {
    private Long id;
    private String nombre;
    private TipoEspacioFisico tipoEspacio;
    private boolean disponible;
}
