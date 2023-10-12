package com.techtrove.ecommerce.core;

import org.apache.catalina.Container;
import org.apache.catalina.core.StandardHost;
import org.springframework.boot.autoconfigure.websocket.servlet.TomcatWebSocketServletWebServerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfig {

    /**errorValveCustomizer.
     * @return TomcatWebSocketServletWebServerCustomizer
     * */
    @Bean
    public TomcatWebSocketServletWebServerCustomizer errorValveCustomizer() {
        return new TomcatWebSocketServletWebServerCustomizer() {
            @Override
            public void customize(TomcatServletWebServerFactory factory) {
                factory.addContextCustomizers((context) -> {
                    Container parent = context.getParent();
                    if (parent instanceof StandardHost) {
                        ((StandardHost) parent).
                                setErrorReportValveClass(
                                        "com.techtrove.ecommerce.core.utils.JsonErrorReportValve");
                    }
                });
            }
            @Override
            public int getOrder() {
                return 100; // needs to be AFTER the one configured with TomcatWebServerFactoryCustomizer
            }
        };
    }

}
