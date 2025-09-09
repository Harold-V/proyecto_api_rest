package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories;

import java.util.*;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.AsignaturaEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.CursoEntity;

/**
 * @class CursoRepository
 * @brief Repositorio en memoria para CursoEntity (grupos A, B, C...).
 */
@Repository("IDCursoRepository")
public class CursoRepository {

    private final Map<Long, CursoEntity> mapaCursos = new HashMap<>();

    public CursoRepository() {
        cargarCursos();
    }

    public Optional<CursoEntity> findById(Long id) {
        return Optional.ofNullable(mapaCursos.get(id));
    }

    public Optional<Collection<CursoEntity>> findAll() {
        return mapaCursos.isEmpty() ? Optional.empty() : Optional.of(mapaCursos.values());
    }

    private void cargarCursos() {
        System.out.println("Cargando cursos iniciales...");

        AsignaturaEntity asignatura1 = new AsignaturaEntity(1L, "IS1234", "Estructuras de Datos");

        AsignaturaEntity asignatura2 = new AsignaturaEntity(2L, "IS2345", "Bases de Datos");

        AsignaturaEntity asignatura3 = new AsignaturaEntity(3L, "IS3456", "Arquitectura de Software");
        mapaCursos.put(1L, new CursoEntity(1L, "IS1234-A", "Estructuras de Datos - Grupo A", "A", asignatura1));
        mapaCursos.put(2L, new CursoEntity(2L, "IS1234-B", "Estructuras de Datos - Grupo B", "B", asignatura1));
        mapaCursos.put(3L, new CursoEntity(3L, "IS2345-A", "Bases de Datos - Grupo A", "A", asignatura2));
        mapaCursos.put(4L, new CursoEntity(4L, "IS2345-B", "Bases de Datos - Grupo B", "B", asignatura2));
        mapaCursos.put(5L, new CursoEntity(5L, "IS3456-A", "Arquitectura de Software - Grupo A", "A", asignatura3));
        mapaCursos.put(6L, new CursoEntity(6L, "IS3456-B", "Arquitectura de Software - Grupo B", "B", asignatura3));
    }

}
