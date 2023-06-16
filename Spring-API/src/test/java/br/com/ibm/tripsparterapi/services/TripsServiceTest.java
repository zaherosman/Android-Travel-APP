package br.com.ibm.tripsparterapi.services;

import br.com.ibm.tripsparterapi.domain.trip.TripsDto;
import br.com.ibm.tripsparterapi.entities.Trips;
import br.com.ibm.tripsparterapi.repositories.TripsRepository;
import br.com.ibm.tripsparterapi.services.exceptions.TripException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

public class TripsServiceTest {

    private Integer id = 1;
    private String name = "sao paulo";
    private String img = "url";
    private String country = "brasil";

    @InjectMocks
    private TripsService tripsService;

    @Mock
    private TripsRepository tripsRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInsertTrip(){
        TripsDto tripsDto = new TripsDto(id, name, img, country);
        Mockito.when(tripsRepository.findByName(ArgumentMatchers.eq(name))).thenReturn(Optional.empty());

        Trips trips = new Trips(id, name, img, country);
        Mockito.when(tripsRepository.save(ArgumentMatchers.any())).thenReturn(trips);

        TripsDto tripsDto1 = tripsService.insertTrip(tripsDto);
        Mockito.verify(tripsRepository).findByName(ArgumentMatchers.eq(name));
        Mockito.verify(tripsRepository).save(ArgumentMatchers.any());
        Assertions.assertEquals(id, tripsDto1.getTripId());
        Assertions.assertEquals(name, tripsDto1.getName());
        Assertions.assertEquals(img, tripsDto1.getImg());
        Assertions.assertEquals(country, tripsDto1.getCountry());
    }

    @Test
    public void testInsertTripException(){
        String name1 = "Grecia";
        TripsDto tripsDto = new TripsDto(id, name, img, country);
        Trips trips = new Trips(id, name, img, country);

        Mockito.when(tripsRepository.findByName(ArgumentMatchers.eq(name))).thenReturn(Optional.of(trips));
        TripException tripException = Assertions.assertThrows(TripException.class, () ->{
            tripsService.insertTrip(tripsDto);
        }, "ExistingTripException error was expected");
        Mockito.verify(tripsRepository).findByName(ArgumentMatchers.eq(name));
        Assertions.assertNotNull(tripException);
    }

    @Test
    public void testGetAllTrips(){
        List<Trips> trips = Arrays.asList(new Trips(id, name, img, country));
        Mockito.when(tripsRepository.findAll()).thenReturn(trips);

        List<TripsDto> expected = tripsService.getAllTrips();

        Mockito.verify(tripsRepository).findAll();

        Assertions.assertEquals(expected.size(), 1);
        Assertions.assertEquals(expected.get(0).getTripId(), trips.get(0).getTripId());
        Assertions.assertEquals(expected.get(0).getName(), trips.get(0).getName());
        Assertions.assertEquals(expected.get(0).getImg(), trips.get(0).getImg());
        Assertions.assertEquals(expected.get(0).getCountry(), trips.get(0).getCountry());
    }

    @Test
    public void testGetAllTripsEmpty(){
        List<Trips> trips = new ArrayList<>();
        Mockito.when(tripsRepository.findAll()).thenReturn(trips);

        List<TripsDto> expected = tripsService.getAllTrips();

        Mockito.verify(tripsRepository).findAll();

        Assertions.assertEquals(expected.size(), 0);
    }

    @Test
    public void TestGetTripByName(){
        String namee= "juse";
        Optional<Trips> trips = Optional.of(new Trips(id, name, img, country));

        Mockito.when(tripsRepository.findByName(ArgumentMatchers.eq(name))).thenReturn(trips);

        Trips expected = tripsService.getTripByName(name);

        Mockito.verify(tripsRepository).findByName(ArgumentMatchers.eq(name));

        Assertions.assertEquals(expected.getName(), trips.get().getName());
    }


    @Test
    public void TestGetTripByNameException(){
        Optional<Trips> trips = Optional.empty();
        Mockito.when(tripsRepository.findByName(ArgumentMatchers.eq(name))).thenReturn(trips);

        TripException tripException = Assertions.assertThrows(TripException.class, ()-> {
            tripsService.getTripByName(name);
        }, "ExistingTripException error was expected");

        Mockito.verify(tripsRepository).findByName(ArgumentMatchers.eq(name));

        Assertions.assertNotNull(tripException);

    }

    @Test
    public void TestGetTripById(){
        Integer id1=2;
        Optional<Trips> trips = Optional.of(new Trips(id, name, img, country));

        Mockito.when(tripsRepository.findById(ArgumentMatchers.eq(id))).thenReturn(trips);

        Trips expected = tripsService.getTripById(id);

        Mockito.verify(tripsRepository).findById(ArgumentMatchers.eq(id));

        Assertions.assertEquals(expected.getTripId(), trips.get().getTripId());
    }

    @Test
    public void TestGetTripByIdException(){
        Optional<Trips> trips = Optional.empty();
        Mockito.when(tripsRepository.findById(ArgumentMatchers.eq(id))).thenReturn(trips);

        TripException tripException = Assertions.assertThrows(TripException.class, ()-> {
            tripsService.getTripById(id);
        }, "ExistingTripException error was expected");

        Mockito.verify(tripsRepository).findById(ArgumentMatchers.eq(id));

        Assertions.assertNotNull(tripException);
    }



    @Test
    public void DeleteUser(){
        Trips trips = new Trips(id, name, img, country);
        Mockito.when(tripsRepository.findAll()).thenReturn(Collections.singletonList(trips));

        tripsService.deleteTrip(id);

        Mockito.verify(tripsRepository).deleteById(ArgumentMatchers.eq(id));

        Assertions.assertEquals(id, 1);
    }



}
