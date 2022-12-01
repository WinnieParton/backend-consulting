package esgi.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import esgi.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM Person as u JOIN u.roles as r WHERE r.id = ?1 ")
    public List<Person> findByRole(Long roleID);
    
    Boolean existsByIdAndEmail(Long id, String email);
        
}
