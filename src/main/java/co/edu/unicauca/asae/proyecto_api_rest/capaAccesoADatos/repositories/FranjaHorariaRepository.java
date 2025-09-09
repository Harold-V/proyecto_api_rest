package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.enums.DiaSemana;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.DocenteEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FranjaHorariaEntity;

@Repository("IDFranjaHorariaRepository")
public class FranjaHorariaRepository {

    private final Map<Long, FranjaHorariaEntity> mapaFranjas = new HashMap<>();
    private final AtomicLong secuencia = new AtomicLong(0L);
    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("HH:mm");

    public Optional<Collection<FranjaHorariaEntity>> findAll() {
        return mapaFranjas.isEmpty() ? Optional.empty() : Optional.of(mapaFranjas.values());
    }

    public Optional<FranjaHorariaEntity> findById(Long id) {
        return Optional.ofNullable(mapaFranjas.get(id));
    }

    public FranjaHorariaEntity save(FranjaHorariaEntity entidad) {
        if (entidad.getId() == null)
            entidad.setId(secuencia.incrementAndGet());
        mapaFranjas.put(entidad.getId(), entidad);
        return entidad;
    }

    public Optional<FranjaHorariaEntity> update(Long id, FranjaHorariaEntity entidad) {
        if (!mapaFranjas.containsKey(id))
            return Optional.empty();
        entidad.setId(id);
        mapaFranjas.put(id, entidad);
        return Optional.of(entidad);
    }

    public boolean delete(Long id) {
        return mapaFranjas.remove(id) != null;
    }

    public List<FranjaHorariaEntity> findAllByCursoId(Long cursoId) {
        return mapaFranjas.values().stream()
                .filter(f -> f.getCurso() != null && Objects.equals(f.getCurso().getId(), cursoId))
                .sorted(Comparator
                        .comparing(FranjaHorariaEntity::getDia)
                        .thenComparing(f -> LocalTime.parse(f.getHoraInicio(), F)))
                .collect(Collectors.toList());
    }

    public boolean existeSolapeEnEspacio(Long espacioId, DiaSemana dia,
            String nuevoInicio, String nuevoFin,
            Long excluirFranjaId) {
        LocalTime nIni = LocalTime.parse(nuevoInicio, F);
        LocalTime nFin = LocalTime.parse(nuevoFin, F);

        for (FranjaHorariaEntity f : mapaFranjas.values()) {
            if (!Objects.equals(f.getEspacioFisico().getId(), espacioId))
                continue;
            if (f.getDia() != dia)
                continue;
            if (excluirFranjaId != null && Objects.equals(f.getId(), excluirFranjaId))
                continue;

            LocalTime eIni = LocalTime.parse(f.getHoraInicio(), F);
            LocalTime eFin = LocalTime.parse(f.getHoraFin(), F);

            if (solapan(nIni, nFin, eIni, eFin))
                return true;
        }
        return false;
    }

    public boolean existeSolapeParaAlgunDocente(Set<Long> docenteIds, DiaSemana dia,
            String nuevoInicio, String nuevoFin,
            Long excluirFranjaId) {
        if (docenteIds == null || docenteIds.isEmpty())
            return false;

        LocalTime nIni = LocalTime.parse(nuevoInicio, F);
        LocalTime nFin = LocalTime.parse(nuevoFin, F);

        for (FranjaHorariaEntity f : mapaFranjas.values()) {
            if (f.getDia() != dia)
                continue;
            if (excluirFranjaId != null && Objects.equals(f.getId(), excluirFranjaId))
                continue;

            boolean comparteDocente = f.getDocentes().stream()
                    .map(DocenteEntity::getId)
                    .anyMatch(docenteIds::contains);

            if (!comparteDocente)
                continue;

            LocalTime eIni = LocalTime.parse(f.getHoraInicio(), F);
            LocalTime eFin = LocalTime.parse(f.getHoraFin(), F);

            if (solapan(nIni, nFin, eIni, eFin))
                return true;
        }
        return false;
    }

    private boolean solapan(LocalTime nIni, LocalTime nFin, LocalTime eIni, LocalTime eFin) {
        return nIni.isBefore(eFin) && nFin.isAfter(eIni);
    }
}
