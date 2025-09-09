package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations;

public abstract class BaseValidadorFranja implements ValidadorFranja {
    private ValidadorFranja next;

    @Override
    public void setNext(ValidadorFranja next) {
        this.next = next;
    }

    @Override
    public void validate(FranjaValidationContext ctx) {
        this.doValidate(ctx);
        if (next != null)
            next.validate(ctx);
    }

    protected abstract void doValidate(FranjaValidationContext ctx);
}
