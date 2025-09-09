package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.enums.DiaSemana;

@Component
public class HorarioPermitidoValidator extends BaseValidadorFranja {
    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("HH:mm");
    private static final LocalTime LIM_INI = LocalTime.of(6, 0);
    private static final LocalTime LIM_FIN = LocalTime.of(22, 0);

    @Override
    protected void doValidate(FranjaValidationContext ctx) {
        if (ctx.getDia() == DiaSemana.DOMINGO) {
            throw new IllegalArgumentException("No se programan franjas el día DOMINGO");
        }
        LocalTime ini = LocalTime.parse(ctx.getHoraInicio(), F);
        LocalTime fin = LocalTime.parse(ctx.getHoraFin(), F);
        if (!fin.isAfter(ini)) {
            throw new IllegalArgumentException("horaFin debe ser posterior a horaInicio");
        }
        if (ini.isBefore(LIM_INI) || fin.isAfter(LIM_FIN)) {
            throw new IllegalArgumentException("Horario fuera del rango permitido (06:00–22:00)");
        }
    }
}
