// src/main/java/.../fachadaService/services/FranjaHorariaServiceImpl.java
package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.services;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.enums.DiaSemana;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.CursoEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.DocenteEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.EspacioFisicoEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FranjaHorariaEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.CursoRepository;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.DocenteRepository;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.EspacioFisicoRepository;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.FranjaHorariaRepository;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.dto.FranjaHorariaDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FranjaHorariaServiceImpl implements IFranjaHorariaService {

    private final FranjaHorariaRepository franjaRepo;
    private final CursoRepository cursoRepo;
    private final DocenteRepository docenteRepo;
    private final EspacioFisicoRepository espacioRepo;

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("HH:mm");
    private static final LocalTime LIM_INI = LocalTime.of(6, 0); // 06:00
    private static final LocalTime LIM_FIN = LocalTime.of(22, 0); // 22:00

    @Override
    public FranjaHorariaEntity crear(FranjaHorariaDTO dto) {
        validarDTO(dto);
        CursoEntity curso = obtenerCurso(dto.getCursoId());
        EspacioFisicoEntity espacio = obtenerEspacio(dto.getEspacioFisicoId());
        List<DocenteEntity> docentes = obtenerDocentes(dto.getDocentesIds());

        validarHorario(dto.getDia(), dto.getHoraInicio(), dto.getHoraFin());
        validarEspacioActivo(espacio);
        validarDisponibilidadEspacio(null, dto);
        validarDisponibilidadDocentes(null, dto);

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
        // verifica que exista
        FranjaHorariaEntity actual = consultar(id);

        validarDTO(dto);
        CursoEntity curso = obtenerCurso(dto.getCursoId());
        EspacioFisicoEntity espacio = obtenerEspacio(dto.getEspacioFisicoId());
        List<DocenteEntity> docentes = obtenerDocentes(dto.getDocentesIds());

        validarHorario(dto.getDia(), dto.getHoraInicio(), dto.getHoraFin());
        validarEspacioActivo(espacio);
        validarDisponibilidadEspacio(id, dto);
        validarDisponibilidadDocentes(id, dto);

        actual.setDia(dto.getDia());
        actual.setHoraInicio(dto.getHoraInicio());
        actual.setHoraFin(dto.getHoraFin());
        actual.setCurso(curso);
        actual.setEspacioFisico(espacio);
        actual.setDocentes(docentes);

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
        return ids.stream()
                .map(id -> docenteRepo.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Docente no encontrado id=" + id)))
                .collect(Collectors.toList());
    }

    private void validarHorario(DiaSemana dia, String hIni, String hFin) {
        if (dia == DiaSemana.DOMINGO) {
            throw new IllegalArgumentException("No se programan franjas el día DOMINGO");
        }
        LocalTime ini = LocalTime.parse(hIni, F);
        LocalTime fin = LocalTime.parse(hFin, F);
        if (!fin.isAfter(ini)) {
            throw new IllegalArgumentException("horaFin debe ser posterior a horaInicio");
        }
        if (ini.isBefore(LIM_INI) || fin.isAfter(LIM_FIN)) {
            throw new IllegalArgumentException("Horario fuera del rango permitido (06:00–22:00)");
        }
    }

    private void validarEspacioActivo(EspacioFisicoEntity espacio) {
        if (!espacio.isDisponible()) {
            throw new IllegalArgumentException("Espacio físico inactivo/no disponible");
        }
    }

    private void validarDisponibilidadEspacio(Long excluirId, FranjaHorariaDTO dto) {
        boolean ocupado = franjaRepo.existeSolapeEnEspacio(
                dto.getEspacioFisicoId(),
                dto.getDia(),
                dto.getHoraInicio(),
                dto.getHoraFin(),
                excluirId);
        if (ocupado)
            throw new IllegalArgumentException("El espacio físico ya está ocupado en ese horario");
    }

    private void validarDisponibilidadDocentes(Long excluirId, FranjaHorariaDTO dto) {
        Set<Long> docentesSet = new HashSet<>(dto.getDocentesIds());
        boolean algunOcupado = franjaRepo.existeSolapeParaAlgunDocente(
                docentesSet,
                dto.getDia(),
                dto.getHoraInicio(),
                dto.getHoraFin(),
                excluirId);
        if (algunOcupado)
            throw new IllegalArgumentException("Alguno de los docentes no está disponible en ese horario");
    }
}
