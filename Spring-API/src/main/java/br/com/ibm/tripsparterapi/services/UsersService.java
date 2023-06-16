package br.com.ibm.tripsparterapi.services;

import br.com.ibm.tripsparterapi.domain.user.UsersConverter;
import br.com.ibm.tripsparterapi.domain.user.UsersDto;
import br.com.ibm.tripsparterapi.entities.Users;
import br.com.ibm.tripsparterapi.repositories.UsersRepository;
import br.com.ibm.tripsparterapi.services.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;


    //listando todos os usuarios do banco
    public List<UsersDto> getAllUsers(){
        return repository.findAll().stream().map(item -> UsersConverter.toDto(Optional.of(item))).toList();
    }


    //chamando usuario atraves do seu respectivo email
    public Users getUserByEmailAndPass(String email, String password){
        Optional<Users> user = repository.findByEmailAndPass(email, password);
        if (user.isPresent()){
            return user.orElseGet(() -> {return null;});
        }else{
            throw new EmailException("Email is not be null");
        }

    }


    //Inserindo um novo user
    public UsersDto insertUser(UsersDto dto){
        Optional<Users> users = repository.findByEmail(dto.getEmail());
        if(users.isPresent()){
            throw new EmailException("Email is already use");
        }else{
            Users user = UsersConverter.toEntity(Optional.of(dto));
            return UsersConverter.toDto(Optional.of(repository.save(user)));
        }
    }

    public void deleteUser(Integer id){
        repository.deleteById(id);
    }
}
