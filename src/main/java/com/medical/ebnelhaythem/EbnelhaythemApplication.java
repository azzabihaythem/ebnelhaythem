package com.medical.ebnelhaythem;

import com.medical.ebnelhaythem.entity.Clinique;
import com.medical.ebnelhaythem.entity.Role;
import com.medical.ebnelhaythem.entity.SeanceType;
import com.medical.ebnelhaythem.repository.CliniqueRepository;
import com.medical.ebnelhaythem.repository.RoleRepository;
import com.medical.ebnelhaythem.repository.SeanceTypeRepository;
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

	@Autowired
	CliniqueRepository cliniqueRepository;

	@Autowired
	SeanceTypeRepository seanceTypeRepository;

	@PostConstruct
	public void init() {

		List<Role> roleList = roleRepository.findAll();
		logger.info("init *****************");
		if(roleList.isEmpty()){
			roleList.add(new Role(0,"admin"));
			roleList.add(new Role(0,"patient"));
			roleRepository.saveAll(roleList);
		}

		List<Clinique> cliniqueList = cliniqueRepository.findAll();

		if(cliniqueRepository.findAll().size()==0){
			cliniqueRepository.save(new Clinique(1,null,null,true,"CLINIQUE IBN ELHAITHEM","RUE DE L'ENVIRONNEMENT ENNAHAL GABE",
					"1355934/R/A/M000","B02140622014","0056615159",
					"75227816","BANQUE DE TUNISIE","05038000106300019897","00213093",
					"67"));
		}

		if(seanceTypeRepository.findAll().size()==0){
			seanceTypeRepository.save(new SeanceType("SeanceClassic","90654","6346",
					"33000","0"));
		}
	}



		public static void main(String[] args) {
		SpringApplication.run(EbnelhaythemApplication.class, args);
	}

}
