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

    private void cargarAsignaturas() {
        mapaAsignaturas.put(1L, new AsignaturaEntity(1L, "Estructuras de Datos", "IS1234"));
        mapaAsignaturas.put(2L, new AsignaturaEntity(2L, "Bases de Datos", "IS2345"));
        mapaAsignaturas.put(3L, new AsignaturaEntity(3L, "Arquitectura de Software", "IS3456"));
    }
}
