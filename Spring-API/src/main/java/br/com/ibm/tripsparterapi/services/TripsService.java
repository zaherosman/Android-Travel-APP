package br.com.ibm.tripsparterapi.services;

import br.com.ibm.tripsparterapi.domain.trip.TripsConverter;
import br.com.ibm.tripsparterapi.domain.trip.TripsDto;
import br.com.ibm.tripsparterapi.entities.Trips;
import br.com.ibm.tripsparterapi.repositories.TripsRepository;
import br.com.ibm.tripsparterapi.services.exceptions.TripException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripsService {

    @Autowired
    private TripsRepository repository;


    //listando todos os usuarios do banco
    public List<TripsDto> getAllTrips(){
        return repository.findAll().stream().map(item -> TripsConverter.toDto(Optional.of(item))).toList();
    }
    public Trips getTripByName(String name){
        Optional<Trips> trip = repository.findByName(name);
        if (trip.isPresent()){
            return trip.orElseGet(() -> {return null;});
        }else{
            throw new TripException("Trip is not be null");
        }
    }

    public Trips getTripById(Integer id){
        Optional<Trips> trip = repository.findById(id);
        if(trip.isPresent()){
            return trip.orElseGet(() -> {return null;});
        }else{
            throw new TripException("User Trips is not be null");
        }
    }

    //Inserindo um novo user
    public TripsDto insertTrip(TripsDto dto){
        Optional<Trips> trips = repository.findByName(dto.getName());
        if(trips.isPresent()){
            throw new TripException("Trips is already insert");
        }else{
            Trips trip = TripsConverter.toEntity(Optional.of(dto));
            return TripsConverter.toDto(Optional.of(repository.save(trip)));
        }
    }

    public void deleteTrip(Integer id){
        repository.deleteById(id);
    }
}
