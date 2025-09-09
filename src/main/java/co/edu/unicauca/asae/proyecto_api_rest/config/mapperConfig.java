package co.edu.unicauca.asae.proyecto_api_rest.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class mapperConfig {
    @Bean
    public ModelMapper crearBeanMapper() {
        return new ModelMapper();
    }
}
