package co.edu.unicauca.asae.proyecto_api_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations.DocentesDisponiblesValidator;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations.EspacioActivoValidator;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations.EspacioDisponibleValidator;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations.HorarioPermitidoValidator;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.validations.ValidadorFranja;

@Configuration
public class CorConfig {

    @Bean
    public ValidadorFranja cadenaValidadores(HorarioPermitidoValidator v1,
            EspacioActivoValidator v2,
            EspacioDisponibleValidator v3,
            DocentesDisponiblesValidator v4) {
        v1.setNext(v2);
        v2.setNext(v3);
        v3.setNext(v4);
        return v1;
    }
}
