package br.com.ibm.tripsparterapi.services;

import br.com.ibm.tripsparterapi.domain.favorite.FavoriteConverter;
import br.com.ibm.tripsparterapi.domain.favorite.FavoriteDto;
import br.com.ibm.tripsparterapi.entities.Favorite;
import br.com.ibm.tripsparterapi.repositories.FavoriteRepository;
import br.com.ibm.tripsparterapi.services.exceptions.FavoriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritesService {

    @Autowired
    private FavoriteRepository repository;

    public List<FavoriteDto> getAllFavorites(){
        return repository.findAll().stream().map(item -> FavoriteConverter.toDto(Optional.of(item))).toList();
    }

    public Favorite getFavoriteByFavoriteId(Integer id){
        Optional<Favorite> favorite = repository.findByFavoriteId(id);
        if (favorite.isPresent()){
            return favorite.orElseGet(() -> {return null;});
        }else{
            throw new FavoriteException("Favorite is not be null");
        }
    }

    public List<Favorite> getFavoriteByUserId(Integer id){
        Optional<List<Favorite>> favorite = repository.findAllByUserId(id);
        if (favorite.isPresent()) {
            return favorite.orElseGet(() -> {return null;});
        }else{
            throw new FavoriteException("Favorite is not be null");
        }
    }

    public Favorite getFavoriteByUserIdAndTripID(Integer userId, Integer tripId){
        Optional<Favorite> favorite = repository.findByUserIdAndTripId(userId, tripId);
        if(favorite.isPresent()) {
            return favorite.orElseGet(() -> {return null;});
        }else{
            throw new FavoriteException("Favorite is not be null");
        }
    }


    public FavoriteDto insertFavorite(FavoriteDto dto){
        Optional<Favorite> favorite = repository.findByUserIdAndTripId(dto.getUserId(), dto.getTripId());
        if(favorite.isPresent()){
            throw new FavoriteException("Favorite is already save");
        }else{
            Favorite favorite1 = FavoriteConverter.toEntity(Optional.of(dto));
            return FavoriteConverter.toDto(Optional.of(repository.save(favorite1)));
        }
    }

    public void deleteFavorite(Integer id){
        repository.deleteById(id);
    }


}
