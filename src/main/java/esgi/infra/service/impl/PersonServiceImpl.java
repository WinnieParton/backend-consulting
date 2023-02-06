package esgi.infra.service.impl;

import esgi.domain.Person;
import esgi.infra.repository.PersonRepository;
import esgi.infra.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl  implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> listPerson() {
        return personRepository.findAll();
    }
}
