package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories;

import java.util.*;

import org.springframework.stereotype.Repository;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.AsignaturaEntity;

@Repository("IDAsignaturaRepository")
public class AsignaturaRepository {

    private final Map<Long, AsignaturaEntity> mapaAsignaturas = new HashMap<>();

    public AsignaturaRepository() {
        cargarAsignaturas();
    }

    public Optional<AsignaturaEntity> findById(Long id) {
        return Optional.ofNullable(mapaAsignaturas.get(id));
    }

    public Optional<Collection<AsignaturaEntity>> findAll() {
        return mapaAsignaturas.isEmpty() ? Optional.empty() : Optional.of(mapaAsignaturas.values());
    }

    private void cargarAsignaturas() {
        mapaAsignaturas.put(1L, new AsignaturaEntity(1L, "IS1234", "Estructuras de Datos"));
        mapaAsignaturas.put(2L, new AsignaturaEntity(2L, "IS2345", "Bases de Datos"));
        mapaAsignaturas.put(3L, new AsignaturaEntity(3L, "IS3456", "Arquitectura de Software"));
    }

}
