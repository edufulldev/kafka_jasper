package edu.dev.icompras.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "edu.dev.icompras.pedidos.client")
public class ClientsConfig {
}
