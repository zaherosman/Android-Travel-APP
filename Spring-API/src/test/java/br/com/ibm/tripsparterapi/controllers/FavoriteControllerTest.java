package br.com.ibm.tripsparterapi.controllers;

import br.com.ibm.tripsparterapi.domain.favorite.FavoriteConverter;
import br.com.ibm.tripsparterapi.domain.favorite.FavoriteDto;
import br.com.ibm.tripsparterapi.entities.Favorite;
import br.com.ibm.tripsparterapi.entities.Users;
import br.com.ibm.tripsparterapi.repositories.FavoriteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteRepository favoriteRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    private Integer favoriteId = 1;
    private Integer userId = 1;
    private Integer tripId = 1;

    @Test
    public void testInsertTrip() throws Exception{
        Favorite favorite = new Favorite(favoriteId, userId, tripId);
        FavoriteDto favoriteDto = new FavoriteDto(null, userId, tripId);
        Mockito.when(favoriteRepository.save(ArgumentMatchers.any())).thenReturn(favorite);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register-favorites")
                .content(new ObjectMapper().writeValueAsString(favoriteDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")));

        Mockito.verify(favoriteRepository).save(ArgumentMatchers.any());
    }

    @Test
    public void testGetAllTrips() throws Exception{
        List<Favorite> favorites = Arrays.asList(new Favorite(favoriteId, userId, tripId));
        Mockito.when(favoriteRepository.findAll()).thenReturn(favorites);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/register-favorites/allFavorites"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")));

        Mockito.verify(favoriteRepository).findAll();

    }

    @Test
    public void testGetFavoriteById() throws Exception{
        Favorite favorite = new Favorite(favoriteId, userId, tripId);
        Mockito.when(favoriteRepository.findByFavoriteId(ArgumentMatchers.eq(favoriteId))).thenReturn(Optional.of(favorite));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register-favorites/requestById")
                .content(new ObjectMapper().writeValueAsString(favorite))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")));

        Mockito.verify(favoriteRepository).findByFavoriteId(ArgumentMatchers.eq(favoriteId));
    }


    @Test
    public void testGetUserFavoriteById() throws Exception{
        List<Favorite> favorites = Arrays.asList(new Favorite(favoriteId, userId, tripId));
        Mockito.when(favoriteRepository.findAllByUserId(ArgumentMatchers.eq(userId))).thenReturn(Optional.of(favorites));

        Favorite favorite = new Favorite(null, userId, tripId);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register-favorites/requestByUserId")
                .content(new ObjectMapper().writeValueAsString(favorite))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")));

        Mockito.verify(favoriteRepository).findAllByUserId(ArgumentMatchers.eq(userId));
    }

    @Test
    public void testGetFavoriteByUserIdAndTripId() throws Exception{
        Favorite favorite = new Favorite(favoriteId, userId, tripId);
        Mockito.when(favoriteRepository.findByUserIdAndTripId(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(tripId))).thenReturn(Optional.of(favorite));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register-favorites/requestByUserIdAndTripId")
                .content(new ObjectMapper().writeValueAsString(favorite))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")));

        Mockito.verify(favoriteRepository).findByUserIdAndTripId(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(tripId));
    }

    @Test
    public void testDelete()throws Exception{
        Favorite favorite = new Favorite(favoriteId, userId, tripId);
        Mockito.when(favoriteRepository.findById(ArgumentMatchers.eq(favoriteId))).thenReturn(Optional.of(favorite));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/register-favorites/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(favoriteRepository).deleteById(ArgumentMatchers.eq(1));
    }
}
