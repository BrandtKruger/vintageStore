package org.kruger.quarkus.microservices.number;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(title = "Number Microservice",
        description = "This Microservice Generates Book Numbers",
        version = "1.0",
        contact = @Contact(name="B Kruger", url = "http://krugerbdg.com")),
        externalDocs = @ExternalDocumentation(url = "www.test.com", description = "All docs"),
        tags = {
                @Tag(name="api", description = "Public API that can be used by anybody."),
                @Tag(name = "numbers")
        }
)
public class NumberMicroService extends Application {

}
