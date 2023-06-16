package br.com.ibm.tripsparterapi.controllers;

import br.com.ibm.tripsparterapi.controllers.response.ResponseHandler;
import br.com.ibm.tripsparterapi.domain.trip.TripsDto;
import br.com.ibm.tripsparterapi.entities.Trips;
import br.com.ibm.tripsparterapi.services.TripsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping(value = "/register-trips")
public class TripsController {

    @Autowired
    private TripsService service;


    @RequestMapping(value = "/requestByName",method = RequestMethod.POST)
    public ResponseEntity<Object> getName(@RequestBody TripsDto tripsdto){
        Trips trips = service.getTripByName(tripsdto.getName());
        if(trips != null){
            return ResponseEntity.ok().body(trips);
        }else{
            return ResponseHandler.generateResponse("Not Authenticated Trip", HttpStatus.NOT_FOUND, " Ñ OK");
        }
    }

    @RequestMapping(value = "/requestByTripId", method = RequestMethod.POST)
    public ResponseEntity<Object> getTrips(@RequestBody TripsDto tripsdto){
        Trips trips = service.getTripById(tripsdto.getTripId());
        if(trips != null) {
            return ResponseEntity.ok().body(trips);
        }else{
            return ResponseHandler.generateResponse("Not Authenticated trip", HttpStatus.NOT_FOUND, "Ñ ok");
        }
    }

    //chamar todos os usuarios
    @GetMapping("/allTrips")
    public ResponseEntity<List<TripsDto>> getAllTrips(){
        return ResponseEntity.ok(this.service.getAllTrips());
    }

    //salvando trips na url
    @PostMapping
    public ResponseEntity<TripsDto> insertTrips(@RequestBody TripsDto dto){
        dto = this.service.insertTrip(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getTripId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping(value= "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id){
        service.deleteTrip(id);
        return ResponseHandler.generateResponse("Trip deleted", HttpStatus.OK, "id " + id + " succesfuly deleted");
    }
}
