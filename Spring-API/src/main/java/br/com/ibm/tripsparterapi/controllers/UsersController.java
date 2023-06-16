package br.com.ibm.tripsparterapi.controllers;


import br.com.ibm.tripsparterapi.controllers.response.ResponseHandler;
import br.com.ibm.tripsparterapi.domain.user.UsersDto;
import br.com.ibm.tripsparterapi.entities.Users;
import br.com.ibm.tripsparterapi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/register-users")
public class UsersController {
    @Autowired
    private UsersService service;


    //chamar todos os usuarios
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<Object> loginUser(@RequestBody UsersDto userDto){
        Users user = service.getUserByEmailAndPass(userDto.getEmail(), userDto.getPass());
        if(user != null){
            return ResponseEntity.ok().body(user);
            //return ResponseHandler.generateResponse(" Authenticated user", HttpStatus.OK, user);
        }else{
            return ResponseHandler.generateResponse("Not Authenticated user", HttpStatus.NOT_FOUND, " Ñ OK");
        }
    }


    @GetMapping("/allUsers")
    public ResponseEntity<List<UsersDto>> getAllUsers(){
        return ResponseEntity.ok(this.service.getAllUsers());
    }




    //salvando usuario na url
    @PostMapping
    public ResponseEntity<UsersDto> insertUser(@RequestBody UsersDto dto){
        dto = this.service.insertUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId_user()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping(value= "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id){
        service.deleteUser(id);
        return ResponseHandler.generateResponse("User deleted", HttpStatus.OK, "id " + id + " succesfuly deleted");
    }
}
