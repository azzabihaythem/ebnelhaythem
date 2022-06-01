package com.medical.ebnelhaythem;

import com.medical.ebnelhaythem.entity.Role;
import com.medical.ebnelhaythem.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class DataBaseTests {


    @Autowired
    private RoleRepository roleRepository;


    @Test
    public void getUserTest(){
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role(0,"admin"));
        roleList.add(new Role(0,"patient"));
        List<Role>  savedRoleList = roleRepository.saveAll(roleList);
        assertEquals( savedRoleList.size() , 2);
    }

}
