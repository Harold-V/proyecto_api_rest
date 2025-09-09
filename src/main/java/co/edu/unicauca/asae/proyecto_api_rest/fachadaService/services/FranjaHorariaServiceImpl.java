// src/main/java/co/edu/unicauca/asae/proyecto_api_rest/fachadaService/services/FranjaHorariaServiceImpl.java
package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.services;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.CursoEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.DocenteEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.EspacioFisicoEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FranjaHorariaEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.CursoRepository;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.DocenteRepository;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.EspacioFisicoRepository;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.FranjaHorariaRepository;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.dto.FranjaHorariaDTO;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations.FranjaValidationContext;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations.ValidadorFranja;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FranjaHorariaServiceImpl implements IFranjaHorariaService {

    private final FranjaHorariaRepository franjaRepo;
    private final CursoRepository cursoRepo;
    private final DocenteRepository docenteRepo;
    private final EspacioFisicoRepository espacioRepo;

    /**
     * Cabeza de la Cadena de Responsabilidades (CoR), ensamblada en un @Bean
     * (CorConfig).
     */
    private final ValidadorFranja cadenaValidadores;

    @Override
    public FranjaHorariaEntity crear(FranjaHorariaDTO dto) {
        validarDTO(dto);

        CursoEntity curso = obtenerCurso(dto.getCursoId());
        EspacioFisicoEntity espacio = obtenerEspacio(dto.getEspacioFisicoId());
        List<DocenteEntity> docentes = obtenerDocentes(dto.getDocentesIds());

        // Dispara la CoR con todas las reglas de negocio
        FranjaValidationContext ctx = new FranjaValidationContext(
                null, dto.getDia(), dto.getHoraInicio(), dto.getHoraFin(),
                curso, espacio, docentes);
        cadenaValidadores.validate(ctx);

        // Nota: si tu Entity tiene "observaciones", añádelo al constructor/setter
        FranjaHorariaEntity entidad = new FranjaHorariaEntity(
                null,
                dto.getDia(),
                dto.getHoraInicio(),
                dto.getHoraFin(),
                curso,
                espacio,
                docentes);

        return franjaRepo.save(entidad);
    }

    @Override
    public FranjaHorariaEntity consultar(Long id) {
        return franjaRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Franja no encontrada con id=" + id));
    }

    @Override
    public FranjaHorariaEntity actualizar(Long id, FranjaHorariaDTO dto) {
        // Asegura existencia
        FranjaHorariaEntity actual = consultar(id);

        validarDTO(dto);

        CursoEntity curso = obtenerCurso(dto.getCursoId());
        EspacioFisicoEntity espacio = obtenerEspacio(dto.getEspacioFisicoId());
        List<DocenteEntity> docentes = obtenerDocentes(dto.getDocentesIds());

        // Reglas de negocio por CoR (excluyendo la propia franja)
        FranjaValidationContext ctx = new FranjaValidationContext(
                id, dto.getDia(), dto.getHoraInicio(), dto.getHoraFin(),
                curso, espacio, docentes);
        cadenaValidadores.validate(ctx);

        // Actualiza estado
        actual.setDia(dto.getDia());
        actual.setHoraInicio(dto.getHoraInicio());
        actual.setHoraFin(dto.getHoraFin());
        actual.setCurso(curso);
        actual.setEspacioFisico(espacio);
        actual.setDocentes(docentes);
        // Si tienes campo "observaciones" en la Entity:
        // actual.setObservaciones(dto.getObservaciones());

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

    // ===== Helpers =====

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
        // (Formato HH:mm y reglas de horario/solapes se validan en la CoR)
    }

    private CursoEntity obtenerCurso(Long cursoId) {
        return cursoRepo.findById(cursoId)
                .orElseThrow(() -> new NoSuchElementException("Curso no encontrado id=" + cursoId));
    }

    private EspacioFisicoEntity obtenerEspacio(Long espacioId) {
        return espacioRepo.findById(espacioId)
                .orElseThrow(() -> new NoSuchElementException("Espacio no encontrado id=" + espacioId));
    }

    private List<DocenteEntity> obtenerDocentes(List<Long> ids) {
        // Evita duplicados y valida existencia uno a uno
        Set<Long> uniq = new HashSet<>(ids);
        return uniq.stream()
                .map(id -> docenteRepo.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Docente no encontrado id=" + id)))
                .collect(Collectors.toList());
    }
}
