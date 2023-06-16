package br.com.ibm.tripsparterapi.domain.trip;

import br.com.ibm.tripsparterapi.entities.Trips;
import java.util.Optional;

public class TripsConverter {

    //constructor
    public TripsConverter() {}

    //converter de dto para entidade
    public static Trips toEntity(Optional<TripsDto> dto){
        if (dto.isPresent()){
            return new Trips(dto.get().getTripId(),
                    dto.get().getName(),
                    dto.get().getImg(),
                    dto.get().getCountry());

        }else{
            throw new IllegalArgumentException("Trip Cannot be null");
        }
    }

    //converter de entidade para dto
    public static TripsDto toDto(Optional<Trips> trip){
        if (trip.isPresent()){
            return new TripsDto(trip.get().getTripId(),
                    trip.get().getName(),
                    trip.get().getImg(),
                    trip.get().getCountry());
        }else{
            throw new IllegalArgumentException("Trip Cannot be null");
        }
    }
}
