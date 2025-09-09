package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.services;

import java.util.List;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FranjaHorariaEntity;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.dto.FranjaHorariaDTO;

public interface IFranjaHorariaService {
    FranjaHorariaEntity crear(FranjaHorariaDTO dto);

    FranjaHorariaEntity consultar(Long id);

    FranjaHorariaEntity actualizar(Long id, FranjaHorariaDTO dto);

    boolean eliminar(Long id);

    List<FranjaHorariaEntity> listarPorCurso(Long cursoId);
}
