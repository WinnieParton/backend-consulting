package esgi.infra.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esgi.domain.ERole;
import esgi.domain.Role;
import esgi.infra.repository.RoleRepository;
import esgi.infra.service.RoleServices;

@Service
public class RoleServiceImpl implements RoleServices {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void CreateRole() {

        List<Role> RoleEntity = roleRepository.findAll();

        if (RoleEntity.size() == 0) {
            Role r1 = new Role(ERole.ROLE_ADMIN);
            Role r2 = new Role(ERole.ROLE_CLIENT);
            Role r3 = new Role(ERole.ROLE_CUSTOMER);
            roleRepository.save(r1);
            roleRepository.save(r2);
            roleRepository.save(r3);
        }

        System.out.print("success");
    }
}
