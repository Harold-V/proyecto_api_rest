package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories;

import java.util.*;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.DocenteEntity;

/**
 * @class DocenteRepository
 * @brief Repositorio en memoria para DocenteEntity.
 */
@Repository("IDDocenteRepository")
public class DocenteRepository {

    private final Map<Long, DocenteEntity> mapaDocentes = new HashMap<>();

    public DocenteRepository() {
        cargarDocentes();
    }

    public Optional<DocenteEntity> findById(Long id) {
        return Optional.ofNullable(mapaDocentes.get(id));
    }

    // y/o:
    public Optional<Collection<DocenteEntity>> findAll() {
        return mapaDocentes.isEmpty() ? Optional.empty() : Optional.of(mapaDocentes.values());
    }

    private void cargarDocentes() {
        System.out.println("Cargando docentes iniciales...");
        mapaDocentes.put(1L, new DocenteEntity(1L, "Juan", "Pérez", "juan@unicauca.edu.co"));
        mapaDocentes.put(2L, new DocenteEntity(2L, "María", "González", "maria@unicauca.edu.co"));
        mapaDocentes.put(3L, new DocenteEntity(3L, "Carlos", "Ramírez", "carlos@unicauca.edu.co"));
    }
}
