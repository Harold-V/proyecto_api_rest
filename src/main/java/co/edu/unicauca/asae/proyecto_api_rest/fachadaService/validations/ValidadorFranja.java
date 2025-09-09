package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations;

public interface ValidadorFranja {
    void setNext(ValidadorFranja next);

    void validate(FranjaValidationContext ctx);
}
