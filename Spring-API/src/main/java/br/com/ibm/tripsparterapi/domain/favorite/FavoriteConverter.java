package br.com.ibm.tripsparterapi.domain.favorite;

import br.com.ibm.tripsparterapi.entities.Favorite;

import java.util.Optional;

public class FavoriteConverter {

    public FavoriteConverter() {
    }

    public static Favorite toEntity(Optional<FavoriteDto> dto){
        if(dto.isPresent()){
            return new Favorite(dto.get().getFavoriteId(),
                    dto.get().getUserId(),
                    dto.get().getTripId());
        }else{
            throw new IllegalArgumentException("Favorite Cannot be null");
        }
    }

    public static FavoriteDto toDto(Optional<Favorite> favUser){
        if(favUser.isPresent()){
            return new FavoriteDto(favUser.get().getFavoriteId(),
                    favUser.get().getUserId(),
                    favUser.get().getTripId());

        }else{
            throw new IllegalArgumentException("Favotite Cannot be null");
        }
    }
}
