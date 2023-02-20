
package esgi.infra.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esgi.domain.Person;
import esgi.infra.repository.PersonRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PersonRepository personRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws RuntimeException {

        Optional<Person> user = personRepository.findByEmail(username);

        System.out.println("username00000000000  ");
        System.out.print(user);
        
        if (user.isPresent())
            return UserDetailsImpl.build(user.get());
        else
            throw new RuntimeException("Person Not Found with email: " + username);
    }
}
