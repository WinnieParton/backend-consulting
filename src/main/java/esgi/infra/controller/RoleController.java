package esgi.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esgi.infra.service.RoleServices;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleServices roleServices;

    @GetMapping
    public ResponseEntity<?> role() {

        roleServices.CreateRole();

        return ResponseEntity.ok("success role");
    }

}
