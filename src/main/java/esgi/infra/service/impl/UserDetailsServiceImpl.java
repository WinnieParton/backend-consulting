
package esgi.infra.service.impl;

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
        Person user = personRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Person Not Found with email: " + username));

        return UserDetailsImpl.build(user);
    }
}
