package esgi.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esgi.domain.ERole;
import esgi.domain.Role;
import esgi.infra.repository.RoleRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/welcome")
public class PersonController {
    @Autowired
    RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<?> home() {

        return ResponseEntity.ok("Welcome successs");
    }

    @GetMapping("/role")
    public ResponseEntity<?> role() {
        Role r=new Role(ERole.ROLE_ADMIN);
        Role r1=new Role(ERole.ROLE_CLIENT);
        Role r3=new Role(ERole.ROLE_CUSTOMER);
        roleRepository.save(r);
        roleRepository.save(r1);
        roleRepository.save(r3);
        return ResponseEntity.ok("Welcome successs");
    }
}
