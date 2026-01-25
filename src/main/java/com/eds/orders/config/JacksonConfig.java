//package com.eds.orders.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import tools.jackson.databind.MapperFeature;
//import tools.jackson.databind.ObjectMapper;
//import tools.jackson.databind.json.JsonMapper;
//
//@Configuration
//public class JacksonConfig {
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        return JsonMapper.builder()
//                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
//                .build();
//    }
//}