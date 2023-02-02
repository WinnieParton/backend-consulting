package esgi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import esgi.infra.service.RoleServices;

@SpringBootApplication
@EnableJpaAuditing
public class BackendConsultingApplication implements CommandLineRunner {
	@Autowired
	private RoleServices roleServices;

	public static void main(String[] args) {
		SpringApplication.run(BackendConsultingApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		roleServices.CreateRole();
	}

}
