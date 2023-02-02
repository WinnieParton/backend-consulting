package esgi.infra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class PersonController {

    @GetMapping
    public ResponseEntity<?> home() {

        return ResponseEntity.ok("Welcome successs");
    }

}
