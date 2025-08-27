package co.edu.unicauca.asae.proyecto_api_rest.fachadaService.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class mapper {
    @Bean
    public ModelMapper crearBeanMapper() {
        return new ModelMapper();
    }
}
