package com.store.authentication.docs;

import com.store.core.docs.BaseSwaggerConfig;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger documentation for the authentication controller api.
 *
 * @author rafaelcolombodesouza
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    /**
     * Constructor.
     */
    public SwaggerConfig() {
        super("com.store.authentication.endpoint.controller");
    }
}
