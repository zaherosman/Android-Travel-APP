package br.com.ibm.tripsparterapi.repositories;

import br.com.ibm.tripsparterapi.entities.Trips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripsRepository extends JpaRepository <Trips, Integer> {

    Optional<Trips> findByName(String name);

    Optional<Trips> findById( Integer id);

}
