/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.adapters.springboot;

import org.keycloak.adapters.tomcat.KeycloakAuthenticatorValve;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Keycloak authentication integration for Spring Boot 2
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(KeycloakSpringBootProperties.class)
@ConditionalOnProperty(value = "keycloak.enabled", matchIfMissing = true)
public class KeycloakAutoConfiguration extends KeycloakBaseSpringBootConfiguration {


    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> getKeycloakContainerCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {
            @Override
            public void customize(ConfigurableServletWebServerFactory configurableServletWebServerFactory) {
                if (configurableServletWebServerFactory instanceof TomcatServletWebServerFactory) {

                    TomcatServletWebServerFactory container = (TomcatServletWebServerFactory) configurableServletWebServerFactory;
                    container.addContextValves(new KeycloakAuthenticatorValve());
                    container.addContextCustomizers(tomcatKeycloakContextCustomizer());

                }
            }

        };
    }

    @Bean
    @ConditionalOnClass(name = {"org.apache.catalina.startup.Tomcat"})
    public TomcatContextCustomizer tomcatKeycloakContextCustomizer() {
        return new KeycloakTomcatContextCustomizer(keycloakProperties);
    }

    static class KeycloakTomcatContextCustomizer extends KeycloakBaseTomcatContextCustomizer implements TomcatContextCustomizer {

        public KeycloakTomcatContextCustomizer(KeycloakSpringBootProperties keycloakProperties) {
            super(keycloakProperties);
        }
    }

}
