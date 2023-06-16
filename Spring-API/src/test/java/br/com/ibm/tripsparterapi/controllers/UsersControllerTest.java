package br.com.ibm.tripsparterapi.controllers;

import br.com.ibm.tripsparterapi.domain.user.UsersDto;
import br.com.ibm.tripsparterapi.entities.Users;
import br.com.ibm.tripsparterapi.repositories.UsersRepository;
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
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersRepository usersRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    private Integer id= 1;
    private String name= "zaher";
    private String email= "ze@ibm,com";
    private String pass= "12345678";

    @Test
    public void testInsertUser() throws Exception{
        Users users = new Users(id, name, email, pass);
        UsersDto usersDto = new UsersDto(null, name, email, pass);
        Mockito.when(usersRepository.save(ArgumentMatchers.any())).thenReturn(users);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register-users")
                .content(new ObjectMapper().writeValueAsString(usersDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(name)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(email)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(pass)));

        Mockito.verify(usersRepository).save(ArgumentMatchers.any());
    }



    @Test
    public void testLoginUser() throws Exception{
        Users users = new Users(null, name, email, pass);
        Mockito.when(usersRepository.findByEmailAndPass(ArgumentMatchers.eq(email), ArgumentMatchers.eq(pass))).thenReturn(Optional.of(users));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/register-users/login")
                        .content(new ObjectMapper().writeValueAsString(users))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(email)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(pass)));

        Mockito.verify(usersRepository).findByEmailAndPass(ArgumentMatchers.eq(email), ArgumentMatchers.eq(pass));
    }



    @Test
    public void testGetAllUsers() throws Exception{
        List<Users> users = Arrays.asList(new Users(id, name, email, pass));
        Mockito.when(usersRepository.findAll()).thenReturn(users);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/register-users/allUsers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(name)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(email)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(pass)));

        Mockito.verify(usersRepository).findAll();
    }

    @Test
    public void testDelete()throws Exception{
        Users users = new Users(id, name, email, pass);
        Mockito.when(usersRepository.findById(ArgumentMatchers.eq(id))).thenReturn(Optional.of(users));

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/register-users/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(usersRepository).deleteById(ArgumentMatchers.eq(1));
    }

}
