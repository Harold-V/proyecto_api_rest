package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories;

import java.util.*;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.enums.TipoEspacioFisico;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.EspacioFisicoEntity;

/**
 * @class EspacioFisicoRepository
 * @brief Repositorio en memoria para EspacioFisicoEntity.
 */
@Repository("IDEspacioFisicoRepository")
public class EspacioFisicoRepository {

    private final Map<Long, EspacioFisicoEntity> mapaEspacios = new HashMap<>();

    public EspacioFisicoRepository() {
        cargarEspacios();
    }

    public Optional<EspacioFisicoEntity> findById(Long id) {
        return Optional.ofNullable(mapaEspacios.get(id));
    }

    public Optional<Collection<EspacioFisicoEntity>> findAll() {
        return mapaEspacios.isEmpty() ? Optional.empty() : Optional.of(mapaEspacios.values());
    }

    private void cargarEspacios() {
        System.out.println("Cargando espacios físicos iniciales...");
        mapaEspacios.put(1L, new EspacioFisicoEntity(1L, "Aula 101", TipoEspacioFisico.AULA, true));
        mapaEspacios.put(2L, new EspacioFisicoEntity(2L, "Aula 102", TipoEspacioFisico.AULA, false));
        mapaEspacios.put(3L,
                new EspacioFisicoEntity(3L, "Laboratorio de Computación", TipoEspacioFisico.LABORATORIO, true));
        mapaEspacios.put(4L, new EspacioFisicoEntity(4L, "Auditorio Principal", TipoEspacioFisico.AUDITORIO, true));
    }
}
