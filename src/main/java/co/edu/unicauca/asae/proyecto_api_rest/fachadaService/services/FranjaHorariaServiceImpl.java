package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FranjaHorariaEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.FranjaHorariaRepository;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.dto.FranjaHorariaDTO;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations.FranjaValidationContext;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations.ValidadorFranja;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FranjaHorariaServiceImpl implements IFranjaHorariaService {

    private final FranjaHorariaRepository franjaRepo;
    private final ValidadorFranja cadenaValidadores; // ensamblada en CorConfig
    private final ModelMapper mapper;

    @Override
    public FranjaHorariaEntity crear(FranjaHorariaDTO dto) {
        validarDTO(dto);

        // DTO -> Entity (resuelve IDs a entidades via convertidores de mapperConfig)
        FranjaHorariaEntity entidad = mapper.map(dto, FranjaHorariaEntity.class);

        // Validaciones de negocio (CoR)
        var ctx = new FranjaValidationContext(
                null,
                entidad.getDia(),
                entidad.getHoraInicio(),
                entidad.getHoraFin(),
                entidad.getCurso(),
                entidad.getEspacioFisico(),
                entidad.getDocentes());
        cadenaValidadores.validate(ctx);

        entidad.setId(null); // id lo asigna el repo en memoria
        return franjaRepo.save(entidad);
    }

    @Override
    public FranjaHorariaEntity consultar(Long id) {
        return franjaRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Franja no encontrada con id=" + id));
    }

    @Override
    public FranjaHorariaEntity actualizar(Long id, FranjaHorariaDTO dto) {
        // asegura existencia
        FranjaHorariaEntity actual = consultar(id);
        validarDTO(dto);

        // Nuevos valores desde DTO
        FranjaHorariaEntity nuevos = mapper.map(dto, FranjaHorariaEntity.class);

        // Valida reglas excluyendo la propia franja
        var ctx = new FranjaValidationContext(
                id,
                nuevos.getDia(),
                nuevos.getHoraInicio(),
                nuevos.getHoraFin(),
                nuevos.getCurso(),
                nuevos.getEspacioFisico(),
                nuevos.getDocentes());
        cadenaValidadores.validate(ctx);

        // Aplica cambios
        actual.setDia(nuevos.getDia());
        actual.setHoraInicio(nuevos.getHoraInicio());
        actual.setHoraFin(nuevos.getHoraFin());
        actual.setCurso(nuevos.getCurso());
        actual.setEspacioFisico(nuevos.getEspacioFisico());
        actual.setDocentes(nuevos.getDocentes());

        return franjaRepo.update(id, actual)
                .orElseThrow(() -> new IllegalStateException("No se pudo actualizar la franja id=" + id));
    }

    @Override
    public boolean eliminar(Long id) {
        return franjaRepo.delete(id);
    }

    @Override
    public List<FranjaHorariaEntity> listarPorCurso(Long cursoId) {
        return franjaRepo.findAllByCursoId(cursoId);
    }

    private void validarDTO(FranjaHorariaDTO dto) {
        if (dto == null)
            throw new IllegalArgumentException("DTO requerido");
        if (dto.getDia() == null)
            throw new IllegalArgumentException("El día es requerido");
        if (!StringUtils.hasText(dto.getHoraInicio()) || !StringUtils.hasText(dto.getHoraFin())) {
            throw new IllegalArgumentException("horaInicio y horaFin son requeridos (HH:mm)");
        }
        if (dto.getCursoId() == null || dto.getEspacioFisicoId() == null) {
            throw new IllegalArgumentException("cursoId y espacioFisicoId son requeridos");
        }
        if (dto.getDocentesIds() == null || dto.getDocentesIds().isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos un docente");
        }
    }
}
