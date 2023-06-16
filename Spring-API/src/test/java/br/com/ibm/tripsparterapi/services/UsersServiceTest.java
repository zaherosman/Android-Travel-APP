package br.com.ibm.tripsparterapi.services;

import br.com.ibm.tripsparterapi.domain.user.UsersDto;
import br.com.ibm.tripsparterapi.entities.Users;
import br.com.ibm.tripsparterapi.repositories.UsersRepository;
import br.com.ibm.tripsparterapi.services.exceptions.EmailException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

public class UsersServiceTest {

    private Integer id = 1;
    private String name = "zaher";
    private String email = "ze@ibm.com";
    private String pass = "12345678";

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }


    //teste para inserir usuario (caminho feliz rsrs)
    @Test
    public void testInsertUser(){
        UsersDto usersDto = new UsersDto(id, name, email, pass);
        Mockito.when(usersRepository.findByEmail(ArgumentMatchers.eq(email))).thenReturn(Optional.empty());

        Users users = new Users(id, name, email, pass);
        Mockito.when(usersRepository.save(ArgumentMatchers.any())).thenReturn(users);

        UsersDto usersDto1 =  usersService.insertUser(usersDto);
        Mockito.verify(usersRepository).findByEmail(ArgumentMatchers.eq(email));
        Mockito.verify(usersRepository).save(ArgumentMatchers.any());
        Assertions.assertEquals(id, usersDto1.getId_user());
        Assertions.assertEquals(name, usersDto1.getName());
        Assertions.assertEquals(email, usersDto1.getEmail());
        Assertions.assertEquals(pass, usersDto1.getPass());
    }

    //teste para inserir usuario (usuario ja existente (caminho triste rsrs))
    @Test
    public void testInsertUserException(){
        String email1 = "juse@ibm.com";
        UsersDto usersDto = new UsersDto(id, name, email, pass);
        Users users = new Users(id, name, email, pass);
        Mockito.when(usersRepository.findByEmail(ArgumentMatchers.eq(email))).thenReturn(Optional.of(users));
        EmailException emailException = Assertions.assertThrows(EmailException.class, () -> {
            usersService.insertUser(usersDto);
        }, "ExistingEmailException error was expected");
        Mockito.verify(usersRepository).findByEmail(ArgumentMatchers.eq(email));
        Assertions.assertNotNull(emailException);
    }



    //teste para chamar todos os usuario (caminho feliz rsrs)
    @Test
    public void testGetAllUsers(){
        List<Users> users = Arrays.asList(new Users(id, name, email, pass));
        Mockito.when(usersRepository.findAll()).thenReturn(users);

        List<UsersDto> expected = usersService.getAllUsers();

        Mockito.verify(usersRepository).findAll();

        Assertions.assertEquals(expected.size(),1);
        Assertions.assertEquals(expected.get(0).getId_user(), users.get(0).getId_user());
        Assertions.assertEquals(expected.get(0).getName(), users.get(0).getName());
        Assertions.assertEquals(expected.get(0).getEmail(), users.get(0).getEmail());
        Assertions.assertEquals(expected.get(0).getPass(), users.get(0).getPass());
    }

    //teste para chamar todos os usuario (vazio (caminho triste rsrs))
    @Test
    public void testGetAllUsersEmpty(){
        List<Users> users = new ArrayList<>();
        Mockito.when(usersRepository.findAll()).thenReturn(users);

        List<UsersDto> expected = usersService.getAllUsers();

        Mockito.verify(usersRepository).findAll();

        Assertions.assertEquals(expected.size(),0);
    }

    //teste para chamar usuario atraves do email (caminho feliz rsrs)
    @Test
    public void TestGetUserByEmailAndPass(){
        String email1 = "juse@gmail.com";
        String pass1 = "123456789";
        Optional<Users> users = Optional.of(new Users(id, name, email, pass));

        Mockito.when(usersRepository.findByEmailAndPass(ArgumentMatchers.eq(email), ArgumentMatchers.eq(pass))).thenReturn(users);

        Users expected = usersService.getUserByEmailAndPass(email, pass);

        Mockito.verify(usersRepository).findByEmailAndPass(ArgumentMatchers.eq(email), ArgumentMatchers.eq(pass));

        Assertions.assertEquals(expected.getEmail(), users.get().getEmail());
        Assertions.assertEquals(expected.getPass(), users.get().getPass());
    }



    @Test
    public void TestGetUserByEmailAndPassException(){

        String email1 = "ze@ibm.com";
        String pass1 = "12345678";

        Optional<Users> users = Optional.of(new Users(id, name, email, pass));
        Mockito.when(usersRepository.findByEmail(ArgumentMatchers.eq(email))).thenReturn(users);

        EmailException emailException = Assertions.assertThrows(EmailException.class, () -> {
            usersService.getUserByEmailAndPass(email, pass);
        }, "ExistingEmailException error was expected");

        Mockito.verify(usersRepository).findByEmailAndPass(ArgumentMatchers.eq(email), ArgumentMatchers.eq(pass));
        Assertions.assertNotNull(emailException);
    }

    @Test
    public void testDeleteUser(){
        Users users = new Users(id, name, email, pass);
        Mockito.when(usersRepository.findAll()).thenReturn(Collections.singletonList(users));

        usersService.deleteUser(id);

        Mockito.verify(usersRepository).deleteById(ArgumentMatchers.eq(id));

        Assertions.assertEquals(id, 1);
    }

}
