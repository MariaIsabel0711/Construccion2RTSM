package app;

import app.adapter.in.client.CliApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RtsmApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RtsmApplication.class);
        app.setWebApplicationType(org.springframework.boot.WebApplicationType.NONE); //configura la aplicación para que no se inicie como una aplicación web
        
        ConfigurableApplicationContext context = app.run(args);
        
        try {
            CliApplication cliApplication = context.getBean(CliApplication.class);
            cliApplication.start();
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación CLI: " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.close();
        }
    }
}
