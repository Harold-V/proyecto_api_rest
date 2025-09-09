package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations;

import org.springframework.stereotype.Component;

@Component
public class EspacioActivoValidator extends BaseValidadorFranja {
    @Override
    protected void doValidate(FranjaValidationContext ctx) {
        if (!ctx.getEspacio().isDisponible()) {
            throw new IllegalArgumentException("Espacio físico inactivo/no disponible");
        }
    }
}
