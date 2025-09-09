package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories;

import java.util.*;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.DocenteEntity;

@Repository("IDDocenteRepository")
public class DocenteRepository {

    private final Map<Long, DocenteEntity> mapaDocentes = new HashMap<>();

    public DocenteRepository() {
        cargarDocentes();
    }

    public Optional<DocenteEntity> findById(Long id) {
        return Optional.ofNullable(mapaDocentes.get(id));
    }

    public Optional<Collection<DocenteEntity>> findAll() {
        return mapaDocentes.isEmpty() ? Optional.empty() : Optional.of(mapaDocentes.values());
    }

    private void cargarDocentes() {
        System.out.println("Cargando docentes iniciales...");
        mapaDocentes.put(1L, new DocenteEntity(1L, "Juan", "Pérez", "juan@unicauca.edu.co"));
        mapaDocentes.put(2L, new DocenteEntity(2L, "María", "González", "maria@unicauca.edu.co"));
        mapaDocentes.put(3L, new DocenteEntity(3L, "Carlos", "Ramírez", "carlos@unicauca.edu.co"));
        mapaDocentes.put(4L, new DocenteEntity(4L, "David", "Martínez", "david@unicauca.edu.co"));
        mapaDocentes.put(5L, new DocenteEntity(5L, "Laura", "Gómez", "laura@unicauca.edu.co"));
    }
}
