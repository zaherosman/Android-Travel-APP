package br.com.ibm.tripsparterapi.controllers;

import br.com.ibm.tripsparterapi.domain.trip.TripsDto;
import br.com.ibm.tripsparterapi.entities.Trips;
import br.com.ibm.tripsparterapi.entities.Users;
import br.com.ibm.tripsparterapi.repositories.TripsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class TripsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripsRepository tripsRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    private Integer id= 1;
    private String name= "Sao paulo";
    private String img= "url";
    private String country= "brasil";

    @Test
    public void testInsertTrip() throws Exception{
        Trips trips = new Trips(id, name, img, country);
        TripsDto tripsDto = new TripsDto(null, name, img, country);
        Mockito.when(tripsRepository.save(ArgumentMatchers.any())).thenReturn(trips);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register-trips")
                .content(new ObjectMapper().writeValueAsString(tripsDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(name)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(img)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(country)));

        Mockito.verify(tripsRepository).save(ArgumentMatchers.any());
    }

    @Test
    public void testGetName() throws Exception{
        Trips trips = new Trips(null, name, img, country);
        Mockito.when(tripsRepository.findByName(ArgumentMatchers.eq(name))).thenReturn(Optional.of(trips));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register-trips/requestByName")
                .content(new ObjectMapper().writeValueAsString(trips))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(name)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(img)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(country)));

        Mockito.verify(tripsRepository).findByName(ArgumentMatchers.eq(name));
    }

    @Test
    public void testGetById() throws Exception{
        Trips trips = new Trips(id, name, img, country);
        Mockito.when(tripsRepository.findById(ArgumentMatchers.eq(id))).thenReturn(Optional.of(trips));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register-trips/requestByTripId")
                .content(new ObjectMapper().writeValueAsString(trips))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(name)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(img)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(country)));

        Mockito.verify(tripsRepository).findById(ArgumentMatchers.eq(id));
    }



    @Test
    public void testGetAllTrips() throws Exception{
        List<Trips> trips = Arrays.asList(new Trips(id, name, img, country));
        Mockito.when(tripsRepository.findAll()).thenReturn(trips);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/register-trips/allTrips"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(name)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(img)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(country)));

        Mockito.verify(tripsRepository).findAll();
    }

    @Test
    public void testDelete()throws Exception{
        Trips trips = new Trips(id, name, img, country);
        Mockito.when(tripsRepository.findById(ArgumentMatchers.eq(id))).thenReturn(Optional.of(trips));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/register-trips/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(tripsRepository).deleteById(ArgumentMatchers.eq(1));
    }
}
