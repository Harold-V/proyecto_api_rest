package co.edu.unicauca.asae.proyecto_api_rest.config;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.CursoEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.DocenteEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.EspacioFisicoEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FranjaHorariaEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.CursoRepository;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.DocenteRepository;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.EspacioFisicoRepository;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.dto.FranjaHorariaDTO;

@Configuration
public class mapperConfig {

    @Bean
    public ModelMapper crearBeanMapper(
            CursoRepository cursoRepo,
            DocenteRepository docenteRepo,
            EspacioFisicoRepository espacioRepo) {

        ModelMapper mm = new ModelMapper();

        Converter<Long, CursoEntity> cursoConv = ctx -> {
            Long id = ctx.getSource();
            if (id == null)
                return null;
            return cursoRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado id=" + id));
        };

        Converter<Long, EspacioFisicoEntity> espacioConv = ctx -> {
            Long id = ctx.getSource();
            if (id == null)
                return null;
            return espacioRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado id=" + id));
        };

        Converter<List<Long>, List<DocenteEntity>> docentesConv = ctx -> {
            List<Long> ids = ctx.getSource();
            if (ids == null)
                return List.of();
            return ids.stream()
                    .distinct()
                    .map(i -> docenteRepo.findById(i)
                            .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado id=" + i)))
                    .collect(Collectors.toList());
        };

        TypeMap<FranjaHorariaDTO, FranjaHorariaEntity> dtoToEntity = mm.createTypeMap(FranjaHorariaDTO.class,
                FranjaHorariaEntity.class);

        dtoToEntity.addMappings(m -> {
            m.using(cursoConv).map(FranjaHorariaDTO::getCursoId, FranjaHorariaEntity::setCurso);
            m.using(espacioConv).map(FranjaHorariaDTO::getEspacioFisicoId, FranjaHorariaEntity::setEspacioFisico);
            m.using(docentesConv).map(FranjaHorariaDTO::getDocentesIds, FranjaHorariaEntity::setDocentes);
        });

        Converter<CursoEntity, Long> cursoToId = ctx -> ctx.getSource() == null ? null : ctx.getSource().getId();
        Converter<EspacioFisicoEntity, Long> espacioToId = ctx -> ctx.getSource() == null ? null
                : ctx.getSource().getId();
        Converter<List<DocenteEntity>, List<Long>> docentesToIds = ctx -> ctx.getSource() == null ? List.of()
                : ctx.getSource().stream().map(DocenteEntity::getId).collect(Collectors.toList());

        TypeMap<FranjaHorariaEntity, FranjaHorariaDTO> entityToDto = mm.createTypeMap(FranjaHorariaEntity.class,
                FranjaHorariaDTO.class);

        entityToDto.addMappings(m -> {
            m.using(cursoToId).map(FranjaHorariaEntity::getCurso, FranjaHorariaDTO::setCursoId);
            m.using(espacioToId).map(FranjaHorariaEntity::getEspacioFisico, FranjaHorariaDTO::setEspacioFisicoId);
            m.using(docentesToIds).map(FranjaHorariaEntity::getDocentes, FranjaHorariaDTO::setDocentesIds);
        });

        return mm;
    }
}
