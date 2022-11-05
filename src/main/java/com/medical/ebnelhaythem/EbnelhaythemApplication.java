package com.medical.ebnelhaythem;

import com.medical.ebnelhaythem.entity.Clinique;
import com.medical.ebnelhaythem.entity.Role;
import com.medical.ebnelhaythem.repository.CliniqueRepository;
import com.medical.ebnelhaythem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class EbnelhaythemApplication {

	private static Logger logger = Logger.getLogger(String.valueOf(EbnelhaythemApplication.class));

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CliniqueRepository cliniqueRepository;

	@PostConstruct
	public void init() {

		List<Role> roleList = roleRepository.findAll();
		logger.info("init *****************");
		if(roleList.isEmpty()){
			roleList.add(new Role(0,"admin"));
			roleList.add(new Role(0,"superadmin"));
			roleList.add(new Role(0,"employer"));
			roleList.add(new Role(0,"patient"));
			roleRepository.saveAll(roleList);
		}

		List<Clinique> cliniqueList = cliniqueRepository.findAll();

		if(cliniqueRepository.findAll().size()==0){
			cliniqueRepository.save(new Clinique(null,null,null,true,"ebnelhaythem","gabes",
					"test","test","test",
					"test","test","test","test",
					"test"));
		}

	}



		public static void main(String[] args) {
		SpringApplication.run(EbnelhaythemApplication.class, args);
	}
/*
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}


 */



}
