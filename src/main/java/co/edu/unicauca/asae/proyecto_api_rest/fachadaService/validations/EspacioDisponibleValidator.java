package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.FranjaHorariaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EspacioDisponibleValidator extends BaseValidadorFranja {

    private final FranjaHorariaRepository franjaRepo;

    @Override
    protected void doValidate(FranjaValidationContext ctx) {
        boolean ocupado = franjaRepo.existeSolapeEnEspacio(
                ctx.getEspacio().getId(),
                ctx.getDia(),
                ctx.getHoraInicio(),
                ctx.getHoraFin(),
                ctx.getExcluirFranjaId());
        if (ocupado) {
            throw new IllegalArgumentException("El espacio físico ya está ocupado en ese horario");
        }
    }
}
