package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RtsmApplication {

    public static void main(String[] args) {
        // Fuerza modo web (SERVLET) y arranca el servidor embebido (Tomcat)
        SpringApplication app = new SpringApplication(RtsmApplication.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);
    }
}
