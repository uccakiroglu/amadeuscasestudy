package net.cagatay.cakiroglu.amadeus_case_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AmadeusCaseStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmadeusCaseStudyApplication.class, args);
	}
}