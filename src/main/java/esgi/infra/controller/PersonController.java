package esgi.infra.controller;

import esgi.infra.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<?> listPerson() {

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("message", " Success: User registered! ");
        map.put("data", personService.listPerson());

        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

}
