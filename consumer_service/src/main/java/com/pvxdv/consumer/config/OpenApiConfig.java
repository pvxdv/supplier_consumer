package com.pvxdv.consumer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Rest Template Example App",
                description = "Home Work #1. Inno.tech.ru course", version = "1.0.0",
                contact = @Contact(
                        name = "Ryzhikh Pavel",
                        email = "rizhikh.pavel@yandex.ru",
                        url = "https://t.me/PVxDV"
                )
        )
)
public class OpenApiConfig {
}
