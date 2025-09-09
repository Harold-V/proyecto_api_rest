package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.services;

import java.util.List;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FranjaHorariaEntity;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.dto.FranjaHorariaDTOPeticion;

public interface IFranjaHorariaService {
    FranjaHorariaEntity crear(FranjaHorariaDTOPeticion dto);

    FranjaHorariaEntity consultar(Long id);

    FranjaHorariaEntity actualizar(Long id, FranjaHorariaDTOPeticion dto);

    boolean eliminar(Long id);

    List<FranjaHorariaEntity> listarPorCurso(Long cursoId);
}
