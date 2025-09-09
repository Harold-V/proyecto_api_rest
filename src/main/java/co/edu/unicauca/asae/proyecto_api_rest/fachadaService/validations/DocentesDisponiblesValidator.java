package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.DocenteEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.FranjaHorariaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DocentesDisponiblesValidator extends BaseValidadorFranja {

    private final FranjaHorariaRepository franjaRepo;

    @Override
    protected void doValidate(FranjaValidationContext ctx) {
        Set<Long> ids = ctx.getDocentes().stream().map(DocenteEntity::getId).collect(Collectors.toSet());
        boolean algunOcupado = franjaRepo.existeSolapeParaAlgunDocente(
                ids, ctx.getDia(), ctx.getHoraInicio(), ctx.getHoraFin(), ctx.getExcluirFranjaId());
        if (algunOcupado) {
            throw new IllegalArgumentException("Alguno de los docentes no está disponible en ese horario");
        }
    }
}
