package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories;

import java.util.*;
import org.springframework.stereotype.Repository;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FranjaHorariaEntity;

/**
 * @class FranjaHorariaRepository
 * @brief Repositorio en memoria para FranjaHorariaEntity,
 *        comparando horas como String en formato HH:mm.
 */
@Repository("IDFranjaHorariaRepository")
public class FranjaHorariaRepository {

    private final Map<Long, FranjaHorariaEntity> mapaFranjas = new HashMap<>();

    public FranjaHorariaRepository() {
    }

    public Optional<Collection<FranjaHorariaEntity>> findAll() {
        return mapaFranjas.isEmpty() ? Optional.empty() : Optional.of(mapaFranjas.values());
    }

    public Optional<FranjaHorariaEntity> findById(Long id) {
        return Optional.ofNullable(mapaFranjas.get(id));
    }
}
