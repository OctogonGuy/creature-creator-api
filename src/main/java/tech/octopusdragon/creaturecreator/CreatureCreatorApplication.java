package tech.octopusdragon.creaturecreator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CreatureCreatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreatureCreatorApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/creature-creator/api").allowedOrigins("http://localhost:5173", "https://octopusdragon.tech");
			}
		};
	}

}
