package esgi.infra.controller;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esgi.domain.ERole;
import esgi.domain.Person;
import esgi.domain.Role;
import esgi.infra.config.JwtUtils;
import esgi.infra.config.response.JwtResponse;
import esgi.infra.config.response.MessageResponse;
import esgi.infra.dto.LoginRequest;
import esgi.infra.dto.RegisterDto;
import esgi.infra.repository.PersonRepository;
import esgi.infra.repository.RoleRepository;
import esgi.infra.service.impl.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),
                userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto signUpRequest) {
        if (Boolean.TRUE.equals(personRepository.existsByEmail(signUpRequest.getEmail()))) {
            return new ResponseEntity<>(
                    new MessageResponse(false, " Error: Email is already in use! " + signUpRequest.getEmail()),
                    HttpStatus.BAD_REQUEST);
        }

        // Create new user's account
        String email = signUpRequest.getEmail().toLowerCase();
        Integer phone = signUpRequest.getPhone();
        String nom = "";
        String prenom = "";

        if (!signUpRequest.getFirstName().isEmpty()) {
            prenom = signUpRequest.getFirstName().substring(0, 1).toUpperCase()
                    + signUpRequest.getFirstName().substring(1).toLowerCase();

        }
        if (!signUpRequest.getLastName().isEmpty()) {
            nom = signUpRequest.getLastName().substring(0, 1).toUpperCase()
                    + signUpRequest.getLastName().substring(1).toLowerCase();
        }

        Person user = new Person(prenom, nom, email, phone, encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException(" Error: Role is not found. " + ERole.ROLE_CUSTOMER));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(
                                        () -> new RuntimeException(" Error: Role is not found. " + ERole.ROLE_ADMIN));
                        roles.add(adminRole);

                        break;

                    case "customer":
                        Role staffRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(
                                        () -> new RuntimeException(
                                                " Error: Role is not found. " + ERole.ROLE_CUSTOMER));
                        roles.add(staffRole);

                        break;
                    default:
                        Role modRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                                .orElseThrow(() -> new RuntimeException(
                                        " Error: Role is not found. " + ERole.ROLE_CLIENT));
                        roles.add(modRole);
                }
            });
        }

        user.setRoles(roles);
        Person res = personRepository.save(user);

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("status", 301);
        map.put("message", " Success: User registered! ");
        map.put("data", res);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
