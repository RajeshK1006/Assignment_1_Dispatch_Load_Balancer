package com.loadBalancer.app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// provides a singleton modelMapper bean for Mapping dto and entities
@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
