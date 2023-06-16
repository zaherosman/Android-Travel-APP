package br.com.ibm.tripsparterapi.domain.user;

import br.com.ibm.tripsparterapi.entities.Users;

import java.util.Optional;

public class UsersConverter {

    //constructor
    public UsersConverter() {}

    //converter de entidade para dto
    public static Users toEntity(Optional<UsersDto> dto){
        if (dto.isPresent()){
            return new Users(dto.get().getId_user(), dto.get().getName(), dto.get().getEmail(), dto.get().getPass());
        }else{
            throw new IllegalArgumentException("User Cannot be null");
        }
    }

    //converter de entidade para dto
    public static UsersDto toDto(Optional<Users> user){
        if (user.isPresent()){
            return new UsersDto(user.get().getId_user(), user.get().getName(), user.get().getEmail(), user.get().getPass());
        }else{
            throw new IllegalArgumentException("User Cannot be null");
        }
    }
}
