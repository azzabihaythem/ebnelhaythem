package com.medical.ebnelhaythem;

import com.medical.ebnelhaythem.entity.Role;
import com.medical.ebnelhaythem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class EbnelhaythemApplication {

	private static Logger logger = Logger.getLogger(String.valueOf(EbnelhaythemApplication.class));

	@Autowired
	RoleRepository roleRepository;

	@PostConstruct
	public void init() {

		List<Role> roleList = roleRepository.findAll();
		logger.info("init *****************");
		if(roleList.isEmpty()){
			roleList.add(new Role(0,"admin"));
			roleList.add(new Role(0,"patient"));
			roleRepository.saveAll(roleList);
		}
	}



		public static void main(String[] args) {
		SpringApplication.run(EbnelhaythemApplication.class, args);
	}

}
